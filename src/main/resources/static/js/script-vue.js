console.log("hi");

let app = new Vue({
    el: "#app",
    data: {
        user: {
            id: '',
            name: '',
            balance: 0,
        },
        current: 0, /// TODO: change this back
        products: [
        ],
        login: {
            username: '',
            password: '',
            error: false,
            success: false,
            loading: false,
        },
        discountsSingle: [

        ],
        discount_color: [
            {r: 23, g: 45, b: 235},
            {r: 239, g: 171, b: 66},
            {r: 0, g: 235, b: 0},
        ],
        discountFlag: [
            false, false, false, false, false, false, false, false, false
        ],
        discountsByProduct: [],
        discountsGlobal: [],
        globalDiscountState: [],
        pay: {
            username: '',
            password: '',
            error: false,
            success: false,
            loading: false,
        },
    },
    methods: {
        handleLogin: function(data) {
            this.login.loading = true;

            let requestBody = {
                name: this.login.username,
                password: this.login.password
            }
        
            let responseBody = $.ajax({
                url: "/login-check",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(requestBody),
                dataType: "json",
                success: (data) => {
                    this.login.success = true;
                    this.login.error = false;
                    // console.log(data);
                    this.user.id = data.id;
                    this.user.name = data.name;
                    this.user.balance = data.balance;
                    setTimeout(() => {
                        this.current = 1;
                        this.loadProducts();
                    }, 500);
                },
                error: (msg) => {
                    this.login.error = true;
                    this.login.success = false;
                    this.login.loading = false;
                    console.log(msg);
                }
            });
        },
        loadProducts: function() {
            this.$Loading.start();
            $.ajax({
                url: "/api/products",
                contentType: "application/json",
                success: (data) => {
                    this.products = data;
                    $.ajax({
                        url: "/api/discounts-single",
                        contentType: "application/json",
                        success: (discount) => {
                            this.discountsSingle = discount;
                            this.products.forEach((curr) => {
                                if (curr.discountId !== null) {
                                    curr.style = this.getDiscountColor(curr.discountId);
                                    curr.discountText = this.dict(curr.discountId);
                                    this.discountsByProduct[curr.id] = this.getDiscountById(curr.discountId);
                                }
                            });
                            $.ajax({
                                url: "/api/discounts-global",
                                contentType: "application/json",
                                success: (discounts) => {
                                    this.discountsGlobal = discounts;
                                    discounts.forEach((curr) => {
                                        let color = this.discount_color[curr.type];
                                        this.globalDiscountState[curr.id] = {
                                            obj: curr,
                                            style: `color: rgb(${color.r}, ${color.g}, ${color.b});`,
                                            select: false,
                                        };
                                    })
                                    this.$Loading.finish();
                                }
                            })
                        }
                    });
                }
            });
        },
        dict: function(id) {
            let curr = this.getDiscountById(id);
            switch (curr.method) {
                case 1: return `打${curr.val1 * 10}折`;
                case 2: return `满${curr.val1}减${curr.val2}`;
                case 3: return `第${curr.val1}件打${curr.val2 * 10}折`;
                case 4: return `优惠${curr.val1}元`;
                default: return "error";
            }
        },
        getDiscountColor: function(id) {
            let curr = this.getDiscountById(id);
            let color = this.discount_color[curr.type];
            return `color: rgb(${color.r}, ${color.g}, ${color.b});`;
        },
        getDiscountById: function(id) {
            return this.discountsSingle.find((i) => {return i.id === id});
        },
        getDiscountStatusById: function(id) {
            return this.discountFlag[this.getDiscountById(id).type];
        },
        calcSingleProduct: function(p) {
            let d = this.getDiscountById(p.discountId);
            if (p.discountSelect) {
                switch (d.method) {
                    case 1: {
                        p.calc = p.price * p.select * d.val1;
                        break;
                    }
                    case 2: {
                        let init = p.price * p.select;
                        if (init > d.val1) init -= d.val2;
                        p.calc = init;
                        break;
                    }
                    case 3: {
                        p.calc = p.price * p.select - Math.floor(p.select / d.val1) * (1 - d.val2) * p.price;
                        break;
                    }
                    case 4: {
                        p.calc = p.price * p.select - d.val1;
                        break;
                    }
                }
            } else {
                p.calc = p.price * p.select;
            }
        },
        calcSingleProductByServer: function(p) {
            $.ajax({
                url: "/api/calc/single-product",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(p),
                dataType: "json",
                success: (data) => {
                    let i = this.products.findIndex((curr) => (curr.id === data.id));
                    this.products[i] = data;
                }
            })
        },
        handleAddAction: function(event) {
            // let index = parseInt(event.target.getAttribute("name"));
            let index = event.currentTarget.name;
            console.log(this.products[index],index);
            if (this.products[index].select < this.products[index].count) {
                this.products[index].select++;
            }
        },
        handleMinusAction: function(event) {
            let index = event.currentTarget.name;
            if (this.products[index].select > 0) {
                this.products[index].select--;
            } else this.products[index].select=0;
            this.calcSingleProduct(this.products[index]);
        },
        handleInputAction: function(event) {
            let index = event.currentTarget.name;
            if (this.products[index].select > this.products[index].count) {
                this.products[index].select = this.products[index].count;
            } else if (this.products[index].select === null) this.products[index].select = 0;
            else if (this.products[index].select * 10 % 10 !== 0) this.products[index].select = Math.floor(this.products[index].select);
            this.calcSingleProduct(this.products[index]);
        },
        handleDiscountSingleChange: function(event) {
            let index = event.currentTarget.name;
            let d = this.discountsByProduct[this.products[index].id];
            this.discountFlag[d.type] = !this.discountFlag[d.type];
            this.calcSingleProduct(this.products[index]);
        }
    },
    computed: {
        originalSum: function() {
            let ret = 0;
            this.products.forEach((curr) => {
                ret += curr.calc;
            });
            return ret;
        },
        finalSum: function() {
            let ret = this.originalSum;
            this.globalDiscountState.forEach((curr) => {
                if (curr.select) {
                    switch (curr.obj.method) {
                        case 1: {
                            ret *= curr.obj.val1;
                            break;
                        }
                        case 2: {
                            if (ret > curr.obj.val1) ret -= curr.obj.val2;
                            break;
                        }
                        case 4: {
                            ret -= curr.obj.val1;
                            break;
                        }
                    }
                }
            });
            if (ret < 0) ret = 0;
            return ret;
        }

    },
    // handlePay: function(data) {
    //     this.pay.loading = true;

    //     let requestBody = {
    //         name: this.user.name,
    //         password: this.pay.password,
    //         cost: this.originalSum
    //     }
    
    //     let responseBody = $.ajax({
    //         url: "/pay-check",
    //         type: "POST",
    //         contentType: "application/json",
    //         data: JSON.stringify(requestBody),
    //         dataType: "json",
    //         success: (data) => {
    //             this.pay.success = true;
    //             this.pay.error = false;
    //             setTimeout(() => {
    //                 this.current = 3;
    //             }, 500);
    //         },
    //         error: (msg) => {
    //             this.pay.error = true;
    //             this.pay.success = false;
    //             this.pay.loading = false;
    //             console.log(msg);
    //         }
    //     });
    // },

});