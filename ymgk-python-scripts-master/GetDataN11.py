import json
import requests
from bs4 import BeautifulSoup
headers={
        "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36"
    }

def getDataN11():
    urlId=1
    for i in range(1000):
        page=requests.get("https://www.vatanbilgisayar.com/cep-telefonu-modelleri/?page="+str(urlId), headers=headers)
        htmlPage= BeautifulSoup(page.content,'html.parser')
        products=htmlPage.find_all("div", class_="product-list product-list--list-page")
        
        for product in products:
            productData={"platformId":"1111","platformName":"Vatan", "name":"", "price":"","url":"", "imageUrl":"", "detail":{},"category":{"id":"e30b27e7-67c5-4f2f-9fd4-5db4df1e282a"}}
            productData["id1"]="1111111"
            productData["url"]="https://www.vatanbilgisayar.com" + product.find("a").get("href")
            productData["price"]=product.find("span", class_="product-list__price").getText().split("TL")[0].replace(".","").replace(",",".")
            productData["name"]+=product.find("h3").getText()
            productData["imageUrl"]=product.find("img").get("data-src")
            # jsondata=json.dumps(getProductDetail(productData))
            jsondata=json.dumps(productData)
            requests.post("http://localhost:8080/product",jsondata)
        urlId+=1
    print("data")
    
def getProductDetail(productData):
    page=requests.get(productData["url"], headers=headers)
    productPage= BeautifulSoup(page.content,'html.parser')
    productsDetail=productPage.find_all("div", class_="row masonry-tab")
    for productDetail in productsDetail:
        if productDetail.find_all("p")[1].get("a"):
            break
        else:
            productData["detail"][productDetail.find_all("p")[0].getText()]=productDetail.find_all("p")[1].getText()
    return productData
getDataN11()