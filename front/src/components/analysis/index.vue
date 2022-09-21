<template>
  <div>
    <studentAnalysis v-if="userInfo.type === 2"></studentAnalysis>
    <teacherAnalysis v-if="userInfo.type === 1"></teacherAnalysis>
    <adminAnalysis v-if="userInfo.type === 0"></adminAnalysis>
  </div>
</template>

<script>
  import studentAnalysis from './student-analysis'
  import teacherAnalysis from './teacher-analysis'
  import adminAnalysis from './admin-analysis'
  export default {
    name: "index",
    components: {
      studentAnalysis, teacherAnalysis, adminAnalysis
    },
    data () {
      return {
        userInfo: {}
      }
    },
    created () {
      this.userInfo = JSON.parse(localStorage.userInfo);
    }
  }
</script>

<style scoped>
</style>
