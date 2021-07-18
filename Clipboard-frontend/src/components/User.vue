<template>
  <div id="message">
    <form action="">
      <p>Message: </p>
      <textarea style="white-space:pre-wrap" id="text" rows="10" cols="30" v-model="addText" />
      <el-button  @click="onSubmit">Add</el-button>
    </form>
    <textarea id="currentText" rows="7" cols="30" v-model="firstItem"/>
    <el-button type="primary"  @click="getCopy">一键复制</el-button><br>
    <table>
      <tr>
        <th>addTime</th>
        <th>msg</th>
      </tr>
      <tr v-for="text in textList" v-bind:key='text.id'>
        <td>{{text.addTime}}</td>
        <td>{{text.msg}}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: 'user',
  data () {
    return {
      addText: '',
      textList: [

      ],
      firstItem: '1'
    }
  },
  // 页面初始化完成后要加载的东西
  created () {
    this.listAll()
    // this指的是当前的vue对象，把vue对象赋给_this
    // const _this = this
    // this.axios.get('http://localhost:8081/findall').then(function (resp) {
    //   // 回调函数中的this指的是回调
    //   // 把data数据赋给_this，即vue对象
    //   console.log(resp.data)
    //   _this.messages = resp.data.data
    //   _this.firstItem = _this.messages[0].msg
    // })
  },
  methods: {
    listAll () {
      const _this = this
      this.axios.get('http://localhost:8090/findall').then(function (resp) {
        // 回调函数中的this指的是回调
        // 把data数据赋给_this，即vue对象
        console.log(resp.data)
        _this.textList = resp.data.data
        _this.firstItem = _this.textList[0].msg
      })
    },
    onSubmit () {
      console.log(this.addText)
      this.axios({
        method: 'post',
        url: 'http://localhost:8090/add',
        headers: {
          'Content-Type': 'application/json'
        },
        data: this.addText
      },
      console.log('post request:' + this.addText)
      ).then((resp) => {
        // eslint-disable-next-line eqeqeq
        if (resp.data.code == '200') {
          this.listAll()
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

</style>
