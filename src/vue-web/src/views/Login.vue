<template>
  <el-form ref="loginForm" :model="loginForm" label-width="100px">
    <el-form-item label="用户名">
      <el-input v-model="loginForm.username"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="loginForm.password"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      },
    };
  },
  methods: {
    submitForm() {
      axios.post('/api/user/login', this.loginForm)
          .then(response => {
            if(response.data.success) {
              this.$message.success('登录成功');
              // 保存token
              localStorage.setItem('token', response.data.token);
              //跳转到TodoList界面
              this.$router.push('/todolist');
            } else {
              this.$message.error(response.data.message);
            }
          })
          .catch(error => {
            this.$message.error('登录失败');
          });
    },
  },
};
</script>
