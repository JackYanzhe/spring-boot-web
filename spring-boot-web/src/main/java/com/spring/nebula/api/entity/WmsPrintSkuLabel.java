package com.spring.nebula.api.entity;

/**
 * 
 * @author zheyan.yan
 *
 */
public class WmsPrintSkuLabel{

    /**
     * 中转仓
     */
    private String warehousename;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 包装指令单号
     */
    private String orderno;
    /**
     * 箱号
     */
    private String boxno;
    /**
     * 单箱件标识
     */
    private String singleboxflage;

    private Data data;

    public class Data{
        /**
         * 产品代码
         */
        private String sku;
        /**
         * 打印数量
         */
        private Integer quantity;
        /**
         * 供应商
         */
        private String supplier;
        /**
         * 产品英文名称
         */
        private String englishname;
        /**
         * 产品名称
         */
        private String skuname;


        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getEnglishname() {
            return englishname;
        }

        public void setEnglishname(String englishname) {
            this.englishname = englishname;
        }

        public String getSkuname() {
            return skuname;
        }

        public void setSkuname(String skuname) {
            this.skuname = skuname;
        }
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getBoxno() {
        return boxno;
    }

    public void setBoxno(String boxno) {
        this.boxno = boxno;
    }

    public String getSingleboxflage() {
        return singleboxflage;
    }

    public void setSingleboxflage(String singleboxflage) {
        this.singleboxflage = singleboxflage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
