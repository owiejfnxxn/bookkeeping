<template>
    <div class="page-register">
        <article class="header">
            <header>
                <el-avatar icon="el-icon-user-solid" shape="circle"></el-avatar>
                <span class="login">
          <em class="bold">已有账号？</em>
          <a href="/login">
            <el-button type="primary" size="small">登录</el-button>
          </a>
        </span>
            </header>
        </article>
        <el-steps :active="active" finish-status="success">
            <el-step title="步骤 1"></el-step>
            <el-step title="步骤 2"></el-step>
        </el-steps>

        <section>
            <el-form
                    ref="ruleForm"
                    :model="ruleForm"
                    :rules="rules"
                    label-width="100px"
                    class="demo-ruleForm"
                    autocomplete="off"
            >
                <div v-if="active==0">
                    <el-form-item prop="textarea">
                        <el-input
                                :value="ruleForm.textarea"
                                type="textarea"
                                :rows="10"
                                :readonly="true"
                        >
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="agreed">
                        <el-checkbox v-model="ruleForm.agreed">同意注册协议</el-checkbox>
                    </el-form-item>
                </div>
                <div v-if="active==1">
                    <el-form-item :error="ruleForm.nameErrMsg" label="用户名" prop="name">
                        <el-input v-model="ruleForm.name" @change="resetName"/>
                    </el-form-item>
                    <el-form-item :error="ruleForm.emailErrMsg" label="邮箱" prop="email">
                        <el-input v-model="ruleForm.email" @change="resetEmail"/>
                        <el-button size="mini" round @click="sendMsg">发送验证码</el-button>
                        <span class="status">{{ statusMsg }}</span>
                    </el-form-item>
                    <el-form-item label="验证码" prop="code">
                        <el-input v-model="ruleForm.code" maxlength="4" />
                    </el-form-item>
                    <el-form-item label="密码" prop="pwd">
                        <el-input v-model="ruleForm.pwd" type="password" />
                    </el-form-item>
                    <el-form-item label="确认密码" prop="cpwd">
                        <el-input v-model="ruleForm.cpwd" type="password" />
                    </el-form-item>

                </div>
            </el-form>
        </section>
        <div class="footer">
            <el-button
                    v-if="active>0"
                    type="primary"
                    icon="el-icon-arrow-left"
                    @click="prev"
            >上一步</el-button>
            <el-button
                    v-if="active<step-1"
                    type="primary"
                    icon="el-icon-arrow-right"
                    @click="next"
            >下一步</el-button>
            <el-button
                    v-if="active==step-1"
                    type="primary"
                    @click="register"
            >同意以下协议并注册</el-button>
            <div class="error">{{ error }}</div>
        </div>
    </div>
</template>

<script>
    import * as userAPI from '@/api/userAPI'
    import {getStore,setStore} from "@/plugins/storage";
    import {Message} from "element-ui";
    import {useUserStore} from "@/store";
    export default {
        data() {
            return {
                step: 2,
                active: 0,
                statusMsg: '',
                error: '',
                ruleForm: {
                    textarea: '请仔细阅读以下协议',
                    agreed: false,
                    name: '',
                    code: '',
                    pwd: '',
                    cpwd: '',
                    email: '',
                    emailErrMsg: '',
                    nameErrMsg:'',
                },
                rules: {
                    agreed: [{
                        validator: (rule, value, callback) => {
                            if (value !== true) {
                                callback(new Error('请确认同意注册协议'))
                            } else {
                                callback()
                            }
                        },
                        trigger: 'blur'
                    }],
                    name: [{
                        required: true,
                        type: 'string',
                        message: '请输入用户名',
                        trigger: 'blur'
                    }],
                    email: [{
                        required: true,
                        type: 'email',
                        message: '请输入邮箱',
                        trigger: 'blur'
                    }],
                    pwd: [{
                        required: true,
                        message: '创建密码',
                        trigger: 'blur'
                    }],
                    cpwd: [{
                        required: true,
                        message: '确认密码',
                        trigger: 'blur'
                    }, {
                        validator: (rule, value, callback) => {
                            if (value === '') {
                                callback(new Error('请再次输入密码'))
                            } else if (value !== this.ruleForm.pwd) {
                                callback(new Error('两次输入密码不一致'))
                            } else {
                                callback()
                            }
                        },
                        trigger: 'blur'
                    }]
                }
            }
        },
        layout: 'blank',
        methods: {
            resetName(){
              this.ruleForm.nameErrMsg='';
            },
            resetEmail(){
                this.ruleForm.emailErrMsg='';
            },
            sendMsg: function() {
                const self = this
                let namePass
                let emailPass
                if (self.timerid) {
                    return false
                }

                this.$refs['ruleForm'].validateField('name', (valid) => {
                    namePass = valid
                })
                self.statusMsg = ''
                if (namePass) {
                    return false
                }
                this.$refs['ruleForm'].validateField('email', (valid) => {
                    emailPass = valid
                })

                // 验证码发送
                if (!namePass && !emailPass) {
                    let count = 60
                    self.statusMsg = `验证码已发送,剩余${count--}秒`
                    self.timerid = setInterval(function() {
                        self.statusMsg = `验证码已发送,剩余${count--}秒`
                        if (count === 0) {
                            clearInterval(self.timerid)
                        }
                    }, 1000)
                    //向后端发送验证码请求
                    userAPI.sendCode(this.ruleForm.email).then(res =>{
                        let data = res.data;
                        if(data.code === 40004){
                            this.ruleForm.emailErrMsg('邮箱已被注册');
                        }else if(data.code === 20006){
                            this.ruleForm.nameErrMsg('用户名已存在');
                        }
                    }).catch(err =>{

                    })
                }
            },

            next: function() {
                if (this.active === 0) {
                    this.$refs['ruleForm'].validateField('agreed', (valid) => {
                        if (valid === '') {
                            this.active++
                        }
                    })
                }
            },
            prev: function() {
                if (--this.active < 0) this.active = 0
            },

            // 注册
            register: function() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        //向后端发送注册请求
                        userAPI.register(this.ruleForm.name,this.ruleForm.pwd,this.ruleForm.email,this.ruleForm.code).then(res =>{
                            let registerInfo = res.data;
                            if(registerInfo.success){
                                Message.info("注册成功....正在登录")
                                setStore('accessToken',registerInfo.data.token);
                                userAPI.userInfo().then(res=>{
                                    let userInfo = res.data;
                                    //设置state数据
                                    useUserStore.state.id=userInfo.id;
                                    useUserStore.state.avatar=userInfo.avatar;
                                    useUserStore.state.totalExpenditure=userInfo.totalExpenditure;
                                    useUserStore.state.name=userInfo.name;
                                    useUserStore.state.nickname=userInfo.nickname;
                                    useUserStore.state.grossIncome = userInfo.grossIncome;

                                    //打开主体页面

                                })
                            }

                        })
                    }
                })
            }
        }
    }
</script>

<style  rel="stylesheet/css" lang="less">
  .page-register {
    //background-color: white;
    .header {
      //border-bottom: 2px solid rgb(235, 232, 232);
      min-width: 980px;
      color: #666;

      header {
        margin: 0 auto;
        padding: 10px 0;
        width: 980px;

        .login {
          float: right;
        }

        .bold {
          font-style: normal;
        }
      }
    }

    .register {
      color: #1890ff;
    }

    a {
      color: #1890ff;
      text-decoration: none;
      background-color: transparent;
      outline: none;
      cursor: pointer;
      transition: color 0.3s;
    }

    > section {
      margin: 0 auto 30px;
      padding-top: 30px;
      width: 980px;
      min-height: 300px;
      padding-right: 550px;
      box-sizing: border-box;

      .status {
        font-size: 12px;
        margin-left: 20px;
        color: #e6a23c;
      }

      .error {
        color: red;
      }
    }

    .footer{
      text-align: center;
    }
  }
</style>
