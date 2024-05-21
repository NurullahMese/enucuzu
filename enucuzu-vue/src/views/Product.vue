<template>
    <v-container>
        <v-layout>
            <v-flex class="xs12 sm12 md12 lg12">
                <v-card @click="goToProductDetail(index)" v-for="(product,index) in products" :key="product.id" width="15%" class="mt-5 mx-2 elevation-3" style="display:inline-block; overflow:hidden; white-space: nowrap; text-overflow: ellipsis;">
                    <v-img :src="product.imageUrl"></v-img>
                    <v-card-title class="pa-0 text-subtitle-1">{{product.name}}</v-card-title>
                    <v-divider class="mx-4"></v-divider>
                    <v-card-subtitle align="center" class="pa-2" style="font-size:100%">
                        <strong>{{product.price}} TL ({{product.platformName}})</strong>
                    </v-card-subtitle>
                </v-card>
            </v-flex>
        </v-layout>
        <div>
            <label></label>
        </div>
    </v-container>
</template>

<script>

export default {
    data: () => ({
        products:[]
    }),
    methods:{
        async getProduct(){
            const response = await this.axios.get("http://localhost:8080/product/search/findCheapestProduct?name=" + this.$route.query.searchName);
            this.products = response.data._embedded.products
            this.products = JSON.parse(JSON.stringify(this.products))
        },
        goToProductDetail(index) {
            this.$router.push({ path: "/product-detail", query: { productId: this.products[index].id } })
        },
    },
    mounted(){
        this.getProduct()
    }
    
}
</script>

<style>

</style>