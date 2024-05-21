<template>
  <v-app class="mx-auto overflow-hidden" style="height: 100%;">
    <v-app-bar app clipped-left color="primary" style="width: 100%;">
      <v-toolbar-title style="cursor:pointer; color:white" class="ml-16" @click="goToMain()">En Ucuzu</v-toolbar-title>
      <div style="width:45%">
        <v-autocomplete @keyup.enter="goToFilterProduct()" v-model="selected" :loading="loading" :items="items"
          :search-input.sync="search" cache-items class="mx-4 ml-16" flat rounded dark hide-no-data hide-details
          label="What do you want to buy cheap?" solo-inverted>
        </v-autocomplete>
      </div>
      <v-spacer></v-spacer>
      <div v-if="!show" class="mr-16" style="width:40%; text-align:right; display:inline-block;">
        <span style="cursor:pointer; color:white;">{{userName}}</span>
        <v-icon @click="goToLogin()" dark size="200%" right>logout</v-icon>
      </div>
      <div v-if="show" class="mr-16" style="width:40%; text-align:right; display:inline-block;">
        <span style="cursor:pointer; color:white;">Hesap Aç</span>
        <span style="cursor:pointer; color:white;" class="ml-2 mr-5">Giriş Yap</span>
        <v-icon dark size="200%" right>person</v-icon>
      </div>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
      <v-container v-if="show" style=" width:60%;  margin-top: 10%; justify-content: center; align-items: center; ">
        <v-row class="mt-5 mx-2 elevation-5" align="center" style="background-color:#C5CAE9; border-radius: 5px;">
          <v-col cols="6">
            En Ucuzu Sitemize Hoşgeldin.
          </v-col>
          <v-col cols="6" style="border-left: 1px solid red">
            <v-form ref="form" lazy-validation>
              <v-container grid-list-md pa-0>
                <v-layout row wrap>
                  <v-flex class="xs12 sm12 md12 lg12">
                    <v-text-field v-model="customer.userName" hide-details dense outlined label="Kullanıcı Adı"></v-text-field>
                  </v-flex>
                  <v-flex class="xs12 sm12 md12 lg12">
                    <v-text-field type="password" v-model="customer.password" hide-details dense outlined label="Parola"></v-text-field>
                  </v-flex>
                  <v-flex v-if="login" @click="login=!login" class="xs12 sm12 md12 lg12">
                    <span style="cursor:pointer;">Üye İseniz Burdan Giriş Yapınız...</span>
                  </v-flex>
                  <v-flex v-if="!login" @click="login=!login" class="xs12 sm12 md12 lg12">
                    <span style="cursor:pointer;">Kayıt Olmak için Tıklayınız...</span>
                  </v-flex>
                  <v-flex v-if="!login" class="xs4 sm4 md4 lg4 text-center">
                    <v-btn @click="checkUser()" width="130px" color="success">
                      <v-icon left> send </v-icon>
                      Giriş Yap
                    </v-btn>
                  </v-flex>
                  <v-flex v-if="login" class="xs4 sm4 md4 lg4 text-center">
                    <v-btn @click="addCustomer()" width="130px" color="success">
                      <v-icon left> send </v-icon>
                      Üye Ol
                    </v-btn>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-form>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    userName:"",
    show:true,
    search:null,
    loading: false,
    items: [],
    selected: null,
    input:"",
    products:[],
    main:true,
    states:[],
    customer:{userName:"",password:""},
    login:false
  }),
  watch: {
    "selected": function () {
      this.goToFilterProduct()
    },
    search (val) {
      val && val !== this.selected && this.querySelections(val)
    },
    },
  mounted() {
    this.userName = localStorage.getItem('userName')
    if (localStorage.getItem('userName') === "" || localStorage.getItem('userName') === null) { this.show = true }
    else {
      this.show = false
      this.getProduct()
    }
  },
  methods:{
    async getProduct(){
      const response = await this.axios.get("http://localhost:8080/product?size=24")
      this.totalPage = response.data.page.totalPages
      this.totalElements = response.data.page.totalElements
      this.size = response.data.page.size

      this.products = response.data._embedded.products
      response.data._embedded.products.forEach(product => {
        this.states.push(product.name)
      });
    },
    goToFilterProduct() {
      if (this.selected === null || this.selected === "") {
        this.$router.push({ path: "/product", query: { searchName: this.search, main: false } })
        // this.$router.go()
      } 
      else {
        this.$router.push({ path: "/product", query: { searchName: this.selected, main: false } })
        this.$router.go()
      }
    },
    goToProductDetail(index){
      this.$router.push({path: "/product-detail", query:{productId: this.products[index].id, }})
    },
    querySelections (v) {
        this.loading = true
        setTimeout(() => {
          this.items = this.states.filter(e => {
            return (e || '').toLowerCase().indexOf((v || '').toLowerCase()) > -1
          })
          this.loading = false
        }, 500)
    },
    async addCustomer(){
        const response = await this.axios.post("http://localhost:8080/user/search/signIn" , this.customer)
        alert(response)
    },
    async checkUser(){
      const response = await this.axios.get("http://localhost:8080/user/search/signUp?userName=" + this.customer.userName + "&password=" + this.customer.password)
      
      if(response.data){
        this.show=false
        localStorage.setItem('userName', this.customer.userName)
        this.userName = localStorage.getItem('userName')
        this.goToMain()
        this.cleanLoginIput()
      }
      else {
        this.cleanLoginIput()
        alert('Kullanıcı Adı veya Şifre Yanlış!!!')
      }
    },
    cleanLoginIput() {
      this.customer = { userName: "", password: "" }
    },
    goToMain(){
      this.$router.push({path:'/main'})
    },
    goToLogin() {
      this.show=true
      localStorage.clear("userName")
      this.$router.push({ path: '/' })
    },
  }
};
</script>
