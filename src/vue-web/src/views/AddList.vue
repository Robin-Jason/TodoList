<template>
  <el-card class="box-card" style="width: 800px; margin: 0 auto;">
    <div slot="header" class="clearfix">
      <span>Add New Todo Item</span>
    </div>
    <el-form :model="form" :rules="rules" ref="form" :label-position="labelPosition" label-width="180px">
      <div style="margin: 40px;"></div>
      <el-form-item label="Name" prop="itemName" label-width="150px">
        <el-input v-model="form.itemName" placeholder="Enter todo item name" style="width: 400px;margin-left: -140px"></el-input>
      </el-form-item>
      <el-form-item label="Completion Status" prop="completionStatus" label-width="150px">
        <el-switch v-model="form.completionStatus" active-color="#13ce66" inactive-color="#ff4949" style="margin-left: -500px"></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addTodoItem" style="margin-left: -190px">Add Todo Item</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import axios from 'axios'; // 导入axios库，用于发送HTTP请求

export default {
  data() {
    return {
      form: {
        itemName: '',
        completionStatus: false
      },
      rules: {
        itemName: [
          { required: true, message: 'Please enter the todo item name', trigger: 'blur' }
        ]
      },
      labelPosition: 'right' // 默认右对齐
    };
  },
  methods: {
    addTodoItem() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const token = localStorage.getItem('token');
          if (!token) {
            console.error('User not authenticated'); // 在没有token的情况下无法执行添加操作
            return;
          }
          // 构造待办事项对象
          const currentTime = new Date().toISOString();
          const todoItem = {
            name: this.form.itemName,
            creationTime: currentTime,
            completionStatus: this.form.completionStatus
          };
          // 发送HTTP POST请求将新待办事项数据发送到后端
          axios.post('/api/todo/add', todoItem, {
            headers: {
              'Authorization': token
            }
          })
              .then(response => {
                // 处理成功响应
                console.log(response.data); // 可以输出响应信息到控制台
                // 在成功添加后可以进行一些操作，比如跳转到待办事项列表页
                this.$router.push('/todolist');
              })
              .catch(error => {
                // 处理错误响应
                console.error('Error adding todo item:', error);
              });
        }
      });
    }
  }
};
</script>
