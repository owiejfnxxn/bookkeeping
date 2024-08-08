<template>
  <div class="user-settings">
    <h2 class="page-title">用户设置</h2>
    <el-form :model="settings" label-width="120px" class="settings-form">
      <el-collapse>
        <el-collapse-item title="通用设置">
          <el-divider></el-divider>
          <!-- 修改每页数据条数 -->
          <div @click="setPageNum">
            账单分页显示条数
          </div>
          <el-divider></el-divider>
          <!-- 修改默认时间类型 -->
          <div @click="setDefaultTimeType">
            默认时间类型
          </div>
          <el-divider></el-divider>
        </el-collapse-item>

        <el-collapse-item title="类别设置">
          <el-divider></el-divider>
          <div @click="setCategory">编辑类别</div>
          <el-divider></el-divider>
          <div @click="categoryTransform">类别迁移</div>
          <el-divider></el-divider>
        </el-collapse-item>
        <el-collapse-item title="重置">
          <el-divider></el-divider>
          <el-button type="primary" @click="resetSettings">重置通用设置</el-button>
          <el-divider></el-divider>
        </el-collapse-item>
      </el-collapse>


    </el-form>

    <!-- 修改账单分页条数的弹窗 -->
    <el-dialog title="修改账单分页条数" :visible.sync="pageNumDialogVisible" @close="closePageNumDialogVisible" width="30%">
      <el-input-number v-model="settings.pageSize" :min="9" :max="20" :step="1"
                       class="settings-input">
      </el-input-number>
      <br/>
      <br/>
      <br/>
      <el-button type="primary" @click="savePageNum">保存</el-button>
      <el-button type="primary" @click="cancelPageNum">取消</el-button>
    </el-dialog>

    <!-- 修改默认时间类型的弹窗 -->
    <el-dialog title="修改默认时间类型" :visible.sync="defaultTimeTypeDialogVisible" @close="closeDefaultTimeTypeDialogVisible"
               width="30%">
      <el-radio-group v-model="settings.defaultTimeType" class="settings-radio-group"
                      @change="defaultTimeTypeChange">
        <el-radio label="week">按周</el-radio>
        <el-radio label="month">按月</el-radio>
        <el-radio label="year">按年</el-radio>
      </el-radio-group>
      <br/>
      <br/>
      <br/>
      <el-button type="primary" @click="saveDefaultTimeType">保存</el-button>
      <el-button type="primary" @click="cancelDefaultTimeType">取消</el-button>
    </el-dialog>

    <!-- 修改类别的弹窗 -->
    <el-dialog title="修改类别" :visible.sync="setCategoryDialogVisible" @close="closeAddCategoryDialog">
      <div style="display: flex;flex-direction: column">
        <el-switch
            style="display: block; flex: 2;text-align: center"
            v-model="category"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="支出"
            inactive-text="收入"
            @change="categoryTypeChange">
        </el-switch>
      </div>
      <el-table :data="categoryList" style="max-height: 300px;width: 1200px;overflow: hidden" height="550">
        <el-table-column
            prop="category_name"
            label="类别名称">
        </el-table-column>
        <el-table-column
            prop="category_type"
            label="类别类型">
        </el-table-column>
        <el-table-column
            label="操作">
          <template v-slot:header>
            <el-button type="primary" size="mini" @click="addCategoryDialog">添加</el-button>
          </template>
          <template v-slot="scope">
            <el-button type="danger" size="mini" @click="deleteCategory(scope.$index,scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 添加类别的弹窗 -->
      <el-dialog title="添加类别" :visible.sync="dialogVisible" @close="closeAddCategoryDialog" append-to-body width="30%">
        <el-form :model="categoryForm" label-width="80px">
          <el-form-item label="类别名称">
            <el-input v-model="categoryForm.category_name"></el-input>
          </el-form-item>
          <el-form-item label="类别类型">
            <el-radio-group v-model="categoryForm.category_type">
              <el-radio label="支出">支出</el-radio>
              <el-radio label="收入">收入</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCategory">确 定</el-button>
            </span>
      </el-dialog>
    </el-dialog>

    <!-- 类别迁移的弹窗 -->
    <el-dialog title="类别迁移" :visible.sync="transformDV" @close="closeTransformDV" width="30%">
      <el-steps :active="active" finish-status="success" align-center>
      <el-step title="选择类别"></el-step>
      <el-step title="选择记录"></el-step>
      <el-step title="迁出类型"></el-step>

    </el-steps>
      <!-- 选择迁出类型 -->
      <div v-if="active === 0" style="height: 300px">
        <br/>
        <br/>
        <br/>
       <el-switch
            style="display: block; flex: 2;text-align: center"
            v-model="transformType1"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="支出"
            inactive-text="收入"
            @change="transformTypeChange">
        </el-switch>
        <br/>
        <br/>
        <el-select v-model="transformFrom" placeholder="请选择">
          <el-option
              v-for="item in transformList"
              :key="item.category_name"
              :label="item.category_name"
              :value="item.category_id">
          </el-option>
        </el-select>
        <br/>
        <br/>
        <br/>
        <br/>
      </div>
      <!-- 选择记录 -->
      <div v-else-if="active === 1" style="height: 300px">
        <div v-if="transformTypeList === null">
          暂无数据
        </div>
        <div v-else>
          <el-table :data="transformTypeList" style="overflow: auto;height: 250px" @selection-change="doSelectionchange">
          <el-table-column
              type="selection">
          </el-table-column>
          <el-table-column
              prop="date"
              label="日期">
          </el-table-column>
          <el-table-column
              prop="kind"
              label="种类">
          </el-table-column>
          <el-table-column
              prop="amount"
              label="金额">
          </el-table-column>
        </el-table>
        </div>
      </div>
      <!-- 选择迁出类型 -->
      <div v-else-if="active === 2" style="height: 300px">
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <el-select v-model="transformTo" placeholder="请选择">

          <el-option
              v-for="item in transformList.filter(item => item.category_id !== transformFrom)"
              :key="item.category_name"
              :label="item.category_name"
              :value="item.category_id">
          </el-option>
        </el-select>
      </div>
      <!-- 完成 -->
<!--      <div v-else style="height: 300px">test3</div>-->

      <el-button @click="prev" v-show="active > 0">上一步</el-button>

      <el-button @click="next">{{ transformText }}</el-button>
    </el-dialog>
  </div>
</template>

<script>

import * as settingsApi from "@/api/settingsAPI";
import {Message} from "element-ui";
import * as categoryApi from "@/api/homeAPI/categoryApi";
import * as eiApi from "@/api/homeAPI/expenditureIncomeApi";
export default {
  data() {
    return {
      // 迁出的类型
      transformTo: '',
      // 类别迁移中，被选中类别的记录
      transformTypeList:[],
      // 类别迁移中，被选中的记录
      transformSelectedList:[],
      // 类别迁移中，被选中的类别
      transformFrom:"",
      // 类别迁移中，选择类别中的数据
      transformList:[],
      transformType1: true,
      active: 0,
      // 通用设置
      dialogVisible: false,
      pageNumDialogVisible: false,
      defaultTimeTypeDialogVisible: false,
      setCategoryDialogVisible: false,
      category: true,
      transformDV: false,
      settings: {
        pageSize: '',
        defaultTimeType: '',
      },
      categoryList: [],
      categoryForm: {
        category_name: '',
        category_type: ''
      },
    };
  },
  methods: {
    doSelectionchange(val) {
      this.transformSelectedList = val;
    },
    transformTypeChange() {
      this.transformFrom = '';
      if (this.transformType1) {
        this.transformList = this.$store.state.categoryList.filter(item => item.category_type === '支出')
      } else {
        this.transformList = this.$store.state.categoryList.filter(item => item.category_type === '收入')
      }
    },
    prev() {
      if (this.active-- < 0) {
        this.active = 0;
      }
    },
    next() {
      // 判断是否选择了
      switch (this.active) {
        case 0:
          if(this.transformFrom === '' || this.transformFrom === undefined) {
            this.$message({
              type: 'warning',
              message: '请选择迁移类型'
            });
            return;
          }else{
            // 向后段请求该类别的全部记录
            eiApi.detailsbyCategory(this.transformFrom).then(res => {
              this.transformTypeList = res.data.data.list;
            })
          }
          break;
        case 1:
          if(this.transformSelectedList.length === 0) {
            this.$message({
              type: 'warning',
              message: '请选择迁移记录'
            });
            return;
          }
          break;
        case 2:
          if(this.transformTo === '' || this.transformTo === undefined) {
            this.$message({
              type: 'warning',
              message: '请选择迁入类型'
            });
            return;
          }
          break;
      }
      // 判断是否是最后一步
      if (this.active++ > 1) {
        this.$confirm('确定要迁移吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          settingsApi.doTransform(this.transformFrom, this.transformTo, this.transformSelectedList).then(res => {
            this.transformDV = false
            this.$message({
              type: 'success',
              message: '迁移成功!'
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '迁移失败'
          });
        });
      }
    },
    categoryTransform() {
      this.transformDV = true
      this.transformTypeChange()
    },


    saveDefaultTimeType() {
      settingsApi.SaveDefaultTimeType(this.settings.defaultTimeType).then(res => {
        this.settings = res.data.data;
        //输出settings的内容
        this.$store.commit("updateSettings", this.settings);
        this.$message({
          message: "保存成功",
          type: "success"
        });
        this.defaultTimeTypeDialogVisible = false
      }).catch(err => {
        this.$message({
          message: "保存失败",
          type: "error"
        });
      })
    },
    cancelDefaultTimeType() {
      this.defaultTimeTypeDialogVisible = false
    },
    //修改每页条数
    savePageNum() {
      settingsApi.SavePageNum(this.settings.pageSize).then(res => {
        this.settings = res.data.data;
        //输出settings的内容
        this.$store.commit("updateSettings", this.settings);
        this.$message({
          message: "保存成功",
          type: "success"
        });
        this.pageNumDialogVisible = false
      }).catch(err => {
        this.$message({
          message: "保存失败",
          type: "error"
        });
      })
    },
    cancelPageNum() {
      this.pageNumDialogVisible = false
    },
    setPageNum() {
      this.pageNumDialogVisible = true
    },
    setCategory() {
      this.setCategoryDialogVisible = true
    },
    setDefaultTimeType() {
      this.defaultTimeTypeDialogVisible = true
    },
    defaultTimeTypeChange() {
    },
    deleteCategory(index, row) {
      // 确认
      this.$confirm('此操作将永久删除该类别, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        categoryApi.deleteCategory(row)
        this.categoryList.splice(index, 1);
        this.$store.commit("deleteCategoryList", row);
        Message.info('删除成功');
      })
    },

    //显示收入或支出的类别的按钮
    categoryTypeChange() {
      if (this.category) {
        this.categoryList = this.$store.state.categoryList.filter(item => item.category_type === '支出')
      } else {
        this.categoryList = this.$store.state.categoryList.filter(item => item.category_type === '收入')
      }
    },

    // saveSettings() {
    //     settingsApi.SaveSettings(this.settings).then(res => {
    //         this.settings = res.data.data;
    //         //输出settings的内容
    //         this.$store.commit("updateSettings", this.settings);
    //         this.$message({
    //             message: "保存成功",
    //             type: "success"
    //         });
    //     }).catch(err => {
    //         this.$message({
    //             message: "保存失败",
    //             type: "error"
    //         });
    //     });
    // },

    resetSettings() {
      this.$confirm('此操作将重置通用设置为默认设置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {

        settingsApi.getDefSettings().then(res => {
          this.settings = res.data.data;
          this.$store.commit("updateSettings", this.settings);
          this.$message({
            message: "重置成功",
            type: "success"
          });
        }).catch(err => {
          console.log(err);
          this.$message({
            message: "重置失败",
            type: "error"
          });
        });

      }).catch(_ => {

      })


    },

    addCategoryDialog() {
      this.dialogVisible = true;
    },

    addCategory() {
      //判断categoryForm中的数据是否已经存在
      if (this.categoryList.some(item => item.category_name === this.categoryForm.category_name)) {
        Message.info('该类别已经存在')
        return;
      }
      categoryApi.addCategory(this.categoryForm).then(res => {
        let category = res.data.data;
        let category2 = {
          category_name: category.categoryName,
          category_type: category.categoryType === '0' ? '支出' : '收入',
          category_id: category.categoryId
        }
        this.categoryList.push(category2)
        this.$store.commit('updateCategoryList', res.data.data)
        this.categoryTypeChange();
        this.closeAddCategoryDialog();
      })

    },
    closePageNumDialogVisible() {
      this.pageNumDialogVisible = false
    },
    closeDefaultTimeTypeDialogVisible() {
      this.defaultTimeTypeDialogVisible = false
    },
    closeTransformDV() {
      // 清空数据
      this.active = 0
      this.transformTo = ''
      this.transformTypeList = []
      this.transformSelectedList = []
      this.transformFrom = ''
      this.transformList = []
      this.transformType1 = true

      this.transformDV = false

    },
    closeAddCategoryDialog() {
      // 关闭添加类别的弹窗
      this.dialogVisible = false;
      //  清空数据
      this.categoryForm.category_name = '';
      this.categoryForm.category_type = '';
    }
  },

  onMounted() {
    this.categoryTypeChange();
  },
  computed: {
    transformText() {
      if (this.active === 0) {
        return '下一步'
      } else if (this.active === 1) {
        return '下一步'
      } else {
        return '完成'
      }
    },
  },
  created() {
    this.settings.defaultTimeType = this.$store.state.defPickerType;
    this.settings.pageSize = this.$store.state.pageSize;
    this.categoryTypeChange();

  }
};
</script>

<style scoped>
.user-settings {
  text-align: center;
  width: 1200px;
  height: 500px;
  min-height: 565px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.page-title {
  transform: translate(0, -100%);
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
}

.settings-form {
  transform: translate(0, -9%);
  height: 85%;
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  overflow: auto;
}

.settings-input {
  width: 150px;
}

.settings-radio-group {
  display: flex;
  align-items: center;
  display: inline;
}

.settings-radio-group .el-radio {
  margin-right: 20px;
}

.save-button {
  margin-top: 20px;
  transform: translate(-5%, 0);
}
</style>
