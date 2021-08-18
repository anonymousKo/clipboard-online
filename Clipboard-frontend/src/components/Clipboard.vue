<template>
  <div id="message">
    <div class = "textarea" >
      <p>Message: </p>
      <el-input
        type="textarea"
        :autosize="{ minRows: 2, maxRows: 6}"
        placeholder="请输入内容"
        v-model="addText">
      </el-input>
      <el-button type="primary" size="medium" @click="onSubmit" round>Add</el-button>
      <el-input
        id = "currentText"
        type="textarea"
        :autosize="{ minRows: 2, maxRows: 6}"
        v-model="firstItem">
      </el-input>
      <el-button type="success" size="medium" @click="getCopy" round>一键复制</el-button>
    </div>

    <el-table
      id="display"
      :data="textList"
      stripe
    >
      <el-table-column
        prop="addTime"
        label="日期"
        min-width="100"
        >
      </el-table-column>
      <el-table-column
        prop="msg"
        label="姓名"
        min-width="250">
      </el-table-column>
        </el-table>
  </div>
</template>

<script>
export default {
  name: 'clipboard',
  data () {
    return {
      addText: '',
      textList: [

      ],
      firstItem: "",
    }
  },
  // 页面初始化完成后要加载的东西
  created () {
    this.listAll()
  },
  methods: {
    listAll () {
      const _this = this
      this.axios.get("/findall").then(function (resp) {
        // 回调函数中的this指的是回调
        // 把data数据赋给_this，即vue对象
        _this.textList = resp.data.data
        _this.firstItem = _this.textList[0].msg
      })
    },
    onSubmit: function () {
      console.log(this.addText)
      document.getElementById("currentText").value=this.addText
      // console.log(this.textList[1])
      var newText={
        addTime:"",
        msg:this.addText
      }
      this.axios({
          method: 'post',
          url: '/add',
          headers: {
            'Content-Type': 'application/json'
          },
          data: this.addText
        },
        console.log('post request:' + this.addText)
      ).then((resp) => {
        // eslint-disable-next-line eqeqeq
        if (resp.data.code == '200') {
          this.textList.unshift("",newText)
          console.log('list all text.')
        }
      })
    },
    async getCopy () {
      let tx = document.getElementById('currentText')
      // 创建select对象与range对象
      const selection = window.getSelection()
      const range = document.createRange()
      // 从当前selection对象中移除所有的range对象,
      // 取消所有的选择只 留下anchorNode 和focusNode属性并将其设置为null。
      // 这里没弄明白为什么需要先remove一下， 也没有太多资料查证 没有这句会复制失败
      if (selection.rangeCount > 0) selection.removeAllRanges()
      // 使 Range 包含某个节点的内容 使用这个 或者下面的selectNode都可以
      // range.selectNodeContents(tx)

      // 使 Range 包含某个节点及其内容
      range.selectNode(tx)
      // 向选区（Selection）中添加一个区域（Range）
      selection.addRange(range)
      // 已复制文字
      // console.log('selectedText', selection.toString())
      // 执行浏览器复制命令
      document.execCommand('copy')
    }
  }
}
</script>

<style scoped>
  #message{
    text-align:center;
    margin-left: 15%;
    margin-right: 15%;
  }
  .textarea{
    margin-left: 12%;
    margin-right: 12%;
  }
  .el-button--medium.is-round {
    margin-top: 5px;
    margin-bottom: 5px;
  }
</style>
