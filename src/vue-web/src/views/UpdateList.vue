<template>
  <el-card class="box-card" style="width: 800px; margin: 0 auto;">
    <div slot="header" class="clearfix">
      <span>Update Todo Item</span>
    </div>
    <el-form :model="form" :rules="rules" ref="form" label-width="180px">
      <!-- 输入待办事项名称 -->
      <el-form-item label="Name" prop="itemName" label-width="180px">
        <el-input v-model="form.itemName" placeholder="Enter todo item name" style="width: 400px;margin-left: -140px"></el-input>
      </el-form-item>
      <!-- 选择完成状态 -->
      <el-form-item label="Completion Status" prop="completionStatus" label-width="180px">
        <el-switch v-model="form.completionStatus" active-color="#13ce66" inactive-color="#ff4949" style="margin-left: -500px"></el-switch>
      </el-form-item>
      <!-- 显示创建时间 -->
      <el-form-item label="Creation Time" label-width="180px">
        <el-date-picker v-model="form.creationTime" type="datetime" placeholder="Select creation time" style="margin-left: -320px"></el-date-picker>
      </el-form-item>
      <!-- 如果事项已完成，显示完成时间选择器 -->
      <el-form-item label="Completion Time" label-width="180px" v-if="form.completionStatus">
        <el-date-picker v-model="form.completionTime" type="datetime" placeholder="Select completion time" style="margin-left: -320px" :picker-options="completionTimeOptions"></el-date-picker>
      </el-form-item>
      <!-- 如果事项未完成，显示“Not Completed Yet” -->
      <el-form-item label="Completion Time" label-width="180px" v-else>
        <el-input v-model="form.completionTimeDisplay" disabled style="width:400px;margin-left: -140px"></el-input>
      </el-form-item>
      <!-- 更新按钮 -->
      <el-form-item>
        <el-button type="primary" @click="updateTodoItem" style="margin-left: -190px">Update Todo Item</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      form: {
        itemId: '',
        itemName: '',
        completionStatus: false,
        creationTime: '',
        completionTime: null,
        completionTimeDisplay: 'Not Completed Yet' // 默认显示为“Not Completed Yet”
      },
      rules: {
        itemName: [
          {required: true, message: 'Please enter the todo item name', trigger: 'blur'}
        ]
      },
      completionTimeOptions: {
        disabledDate: time => {
          // 限制完成时间不能早于创建时间
          return time.getTime() < new Date(this.form.creationTime).getTime();
        }
      }
    };
  },
  methods: {
    // 更新待办事项
    updateTodoItem() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const token = localStorage.getItem('token');
          if (!token) {
            console.error('User not authenticated');
            return;
          }

          // 如果事项未完成，则完成时间设置为null
          const completionTime = this.form.completionStatus ? this.form.completionTime : null;

          // 更新的待办事项对象
          const updatedTodoItem = {
            itemId: this.form.itemId,
            name: this.form.itemName,
            completionStatus: this.form.completionStatus,
            creationTime: this.form.creationTime,
            completionTime: completionTime
          };

          // 发送更新请求
          axios.put(`/api/todo/${this.form.itemId}`, updatedTodoItem, {
            headers: {
              'Authorization': token
            }
          })
              .then(response => {
                console.log('Todo item updated successfully:', response.data);
                this.$router.push('/todolist');
              })
              .catch(error => {
                console.error('Error updating todo item:', error);
              });
        }
      });
    },
    // 加载待办事项数据
    loadTodoItemData() {
      const itemId = this.$route.params.itemId;

      axios.get(`/api/todo/${itemId}`, {
        headers: {
          'Authorization': localStorage.getItem('token')
        }
      })
          .then(response => {
            // 填充表单数据
            this.form.itemId = response.data.itemId;
            this.form.itemName = response.data.name;
            this.form.completionStatus = response.data.completionStatus;
            this.form.creationTime = response.data.creationTime;
            this.form.completionTime = response.data.completionTime;
            // 根据完成状态设置显示的完成时间
            this.form.completionTimeDisplay = response.data.completionStatus ? response.data.completionTime : 'Not Completed Yet';
          })
          .catch(error => {
            console.error('Error loading todo item data:', error);
          });
    }
  },
  created() {
    // 加载待办事项数据
    this.loadTodoItemData();
  }
};
</script>
