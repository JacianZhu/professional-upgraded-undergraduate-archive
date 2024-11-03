<template>
  <div class="app-container home">
    <h1>欢迎使用专升本档案管理系统</h1>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.8"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #73a5ff, #ffffff);
  color: white;
  text-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);

  h1 {
    font-size: 32px;
    font-weight: bold;
    padding: 20px;
    border-radius: 10px;
    background: rgba(255, 255, 255, 0.2);
    box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
  }
}
</style>
