<template>
  <div class="user-info">
    <el-card class="box-card" v-if="userInfo">
      <div slot="header" class="clearfix">
        <span>个人信息</span>
      </div>
      <el-form label-position="left" label-width="100px">
        <el-form-item label="用户名">
          <span>{{ userInfo.username }}</span>
        </el-form-item>
        <el-form-item label="姓名">
          <span>{{ userInfo.name }}</span>
        </el-form-item>
        <el-form-item label="性别">
          <span>{{ userInfo.gender }}</span>
        </el-form-item>
        <el-form-item label="出生日期">
          <span>{{ userInfo.birthDate }}</span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userInfo: null
    };
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    fetchUserInfo() {
      const token = localStorage.getItem('token');
      axios.get('/api/user/info', {
        headers: {
          'Authorization': token
        }
      })
          .then(response => {
            this.userInfo = response.data;
          })
          .catch(error => {
            this.$message.error('获取用户信息失败');
          });
    }
  }
};
</script>

<style scoped>
.user-info {
  margin: 20px;
}
.box-card {
  width: 300px;
}
</style>
