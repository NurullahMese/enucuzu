import Vue from "vue";
import VueRouter from "vue-router";
import Product from "../views/Product.vue";
import ProductDetail from "../views/ProductDetail.vue";
// import Login from "../views/Login.vue";
import Main from "../views/Main.vue";



Vue.use(VueRouter);

const routes = [
  {
    path: "/main",
    name: "Main",
    component: Main,
  },
  {
    path: "/product",
    name: "Product",
    component: Product,
  },
  {
    path: "/Product-detail",
    name: "Product-detail",
    component: ProductDetail,
  },
  // {
  //   path: "/login",
  //   name: "Login",
  //   component: Login,
  // }
];

['push','replace'].forEach(method => {
  const originalMethod = VueRouter.prototype[method];
  VueRouter.prototype[method] = function m(location) {
    return originalMethod.call(this, location).catch(error => {
      if (error.name !== 'NavigationDuplicated') {
        // capture exception
      }
    })
  };
});

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
