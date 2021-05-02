interface Product {
    id: number;
    description: string;
  }
  
  class ProductService {

      // this is the odd way one must do method overloading in typescript
        
      getProducts(description: string): Product[];
      getProducts(id: number): Product;
      getProducts(product: number | string): Product[] | Product{
          if  (typeof product === "number") {
            console.log(`Getting the product info for id ${product}`);
                      return { id: product, description: 'great product' };
          } else if (typeof product === "string")  { 
            console.log(`Getting product with description ${product}`);
            return [{ id: 123, description: 'blue jeans' },
                    { id: 789, description: 'blue jeans' }];
        } else {
          return { id: -1, description: 'Error: getProducts() accept only number or string as args' };
        }   
          
      }
  }
  
  const prodService = new ProductService();
  
  console.log(prodService.getProducts(123));
  
  console.log(prodService.getProducts('blue jeans'));
  