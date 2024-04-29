<template>
  <el-form ref="userForm" :model="userForm" label-width="100px">
    <el-form-item label="用户名">
      <el-input v-model="userForm.username"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="userForm.password"></el-input>
    </el-form-item>
    <el-form-item label="姓名">
      <el-input v-model="userForm.name"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="userForm.gender">
        <el-radio label="男">男</el-radio>
        <el-radio label="女">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="出生日期">
      <el-date-picker v-model="userForm.birthDate" type="date" placeholder="选择日期"></el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">提交</el-button>
      <el-button @click="resetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userForm: {
        username: '',
        password: '',
        name: '',
        gender: '',
        birthDate: '',
      },
    };
  },
  methods: {
    submitForm() {
      axios.post('/api/user/add', this.userForm)
          .then(response => {
            this.$message.success('用户添加成功');
          })
          .catch(error => {
            this.$message.error('用户添加失败');
          });
    },
    resetForm() {
      this.$refs.userForm.resetFields();
    },
  },
};
</script>
