<template>
  <div id="app" :style="screenHeight">
    <router-view />
  </div>
</template>

<script>
  export default {
    data() {
      return {
        screenHeight: "",
      };
    },
    created() {
      let that = this;
      this.screenHeight = {
        height: '100vh'
      };
      window.onresize = function () {
        that.screenHeight = {
          height: '100vh'
        };
      };
    },
  };
</script>

<style>
  body {
    padding: 0;
    margin: 0;
    background-repeat: no-repeat;
    background-color: RGB(249,249,249);
  }

  #app {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    overflow-x: hidden;
    overflow-y: hidden;
  }


  ::-webkit-scrollbar-track-piece {
    background-color: #f8f8f8;
  }

  ::-webkit-scrollbar {
    width: 9px;
    height: 9px;
  }

  ::-webkit-scrollbar-thumb {
    background-color: #dddddd;
    background-clip: padding-box;
    min-height: 28px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background-color: #bbb;
  }
</style>