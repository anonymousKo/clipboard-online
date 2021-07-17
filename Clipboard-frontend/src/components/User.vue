<template>

  <div>
    <form action="http://localhost:8081/add" method="post">
      <p>Message: <input type="text" name="message" /></p>
      <input type="submit" value="Submit" />
    </form>
    <table>
      <tr>
        <th>addTime</th>
        <th>msg</th>
      </tr>
      <tr v-for="item in messages">
<!--        <td>{{item.id}}</td>-->
        <td>{{item.addTime}}</td>
        <td>{{item.msg}}</td>
      </tr>
    </table>
  </div>
</template>


<script>
  export default {
    name: "user",
    data(){
      return {
        messages:[

        ]
      }
    },
    //页面初始化完成后要加载的东西
    created() {
      //this指的是当前的vue对象，把vue对象赋给_this
      const _this=this
      this.axios.get('http://localhost:8081/findall').then(function (resp) {
        //回调函数中的this指的是回调
        //把data数据赋给_this，即vue对象
        _this.messages=resp.data
      })
    }
  }
</script>

<style scoped>

</style>
