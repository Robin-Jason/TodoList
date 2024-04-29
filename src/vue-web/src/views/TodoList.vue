<template>
  <div>
    <h2>My Todo List</h2>
    <el-card class="box-card" v-if="todoItems.length > 0">
      <div slot="header" class="clearfix">
        <!-- 新增待办事项按钮 -->
        <el-button type="primary" @click="goToAddList">Add New Items</el-button>
      </div>
      <el-table :data="todoItems" border style="width: 100%">
        <el-table-column prop="name" label="名称">
          <template slot-scope="scope">
            <span :class="{ 'completed': scope.row.completionStatus }">{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.completionStatus" @change="toggleCompletion(scope.row)" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建时间">
          <template slot-scope="scope">
            <span>{{ formatDate(scope.row.creationTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="完成时间">
          <template slot-scope="scope">
            <span>{{ scope.row.completionStatus ? formatDate(scope.row.completionTime) : 'Not Completed Yet' }}</span>
          </template>
        </el-table-column>
        <!-- 修改按钮列 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="goToUpdateList(scope.row.itemId)">Modify</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <div v-else>
      <p>Nothing Yet</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      todoItems: []
    };
  },
  created() {
    // 页面加载时获取待办事项列表
    this.fetchTodoItems();
  },
  methods: {
    // 新增待办事项按钮点击事件
    goToAddList() {
      // 跳转到新增待办事项组件
      this.$router.push({name: 'addlist'});
    },
    // 修改按钮点击事件
    goToUpdateList(itemId) {
      // 跳转到修改待办事项组件，并传递待办事项的 ID
      this.$router.push({name: 'update', params: {itemId}});
    },
    // 获取待办事项列表
    fetchTodoItems() {
      const token = localStorage.getItem('token');
      if (token) {
        axios.get('/api/todo/list', {
          headers: {
            'Authorization': token
          }
        })
            .then(response => {
              // 将获取到的待办事项列表保存到 todoItems 中
              this.todoItems = response.data;
              console.log(response.data);
            })
            .catch(error => {
              console.error('获取待办事项失败:', error);
            });
      }
    },
    // 切换待办事项的完成状态
    toggleCompletion(item) {
      // 记录原始状态和完成时间
      const originalStatus = item.completionStatus;
      const originalCompletionTime = item.completionTime;

      // 更新状态和时间
      item.completionTime = item.completionStatus ? new Date().toISOString() : null;

      // 如果完成时间早于创建时间，则撤销操作
      if (item.completionTime && new Date(item.creationTime) > new Date(item.completionTime)) {
        // 提示用户完成时间不能早于创建时间
        this.$message.error('Completion time cannot be earlier than creation time. Please click modify to set Completion time.');
        // 恢复原始状态和完成时间
        item.completionStatus = !originalStatus;
        item.completionTime = originalCompletionTime;
        return;
      }

      // 发送更新请求到后端
      axios.put(`/api/todo/${item.itemId}`, item, {
        headers: {
          'Authorization': localStorage.getItem('token')
        }
      })
          .then(response => {
            console.log('更新待办事项成功:', response.data);
            // 更新成功后，重新获取待办事项列表
            this.fetchTodoItems();
          })
          .catch(error => {
            console.error('更新待办事项失败:', error);
            // 如果更新失败，恢复切换前的状态和完成时间
            item.completionStatus = !originalStatus;
            item.completionTime = originalCompletionTime;
          });
    },
    // 格式化日期
    formatDate(date) {
      if (!date) return ''; // 如果日期为空，返回空字符串
      return new Date(date).toLocaleDateString('en-US'); // 将日期格式化为年-月-日的格式
    }
  }
};
</script>

<style scoped>
.completed {
  color: gray;
  text-decoration: line-through;
}
</style>
