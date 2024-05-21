<template>
    <v-row class="pa-0 mt-5 mx-2 elevation-3" width="100%" >
        <v-col width="50%">
            <v-card width="25%" class="elevation-3" style=" margin-left: 10%; display:inline-block">
                <v-img :src="product.imageUrl"></v-img>
                <v-card-title class="pa-0  text-subtitle-1">{{product.name}}</v-card-title>
                <v-divider class="mx-4"></v-divider>
                <v-card-subtitle align="center" class="pa-2" style=" font-size:100%"><strong> {{product.price}} TL
                    </strong> (Trendyol)</v-card-subtitle>
            </v-card>
            <div max-width="35%" style=" margin-left: 10%; display:inline-block;">
                <v-btn class="mt-2" v-for="productOther in products" :key="productOther.id" :href="productOther.url"
                    style="display:block">{{productOther.platformName}}:<span>{{productOther.price}} TL</span></v-btn>
            </div>
        </v-col>
        <v-col width="50%" style="border-left:1px solid gray">
            <table>
                <tr>
                    <th class="text-left">Teknik Özellikler:</th>
                </tr>
                <tr v-for="(productDetail) in Object.entries(this.product.detail)" :key="productDetail.id">
                    <th class="text-left" style="font-size: 90%; width: 60%">{{productDetail[0]}}</th>
                    <th class="text-left" style="width: 3%">:</th>
                    <td style="width: 37%; font-size: 80%;" colspan="2">{{productDetail[1]}}</td>
                </tr>
            </table>
        </v-col>
    </v-row>
</template>

<script>
export default {
    data: () => ({
        product: {},
        products: [],
        searchName:""
    }),
    methods:{
        async getProduct(){
            const response = await this.axios.get("http://localhost:8080/product/search/getProduct?id=" + this.$route.query.productId)
            this.product = response.data._embedded.products[0]

            this.searchName = this.product.name.substring(0, this.product.name.indexOf('('))
            const response2 = await this.axios.get("http://localhost:8080/product/search/findCheapestProduct?name=" + this.searchName);
            this.products = response2.data._embedded.products

        }
    },
    mounted(){
        this.getProduct()
    }
}
</script>