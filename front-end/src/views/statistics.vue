<template>
    <div class="main">
        <div id="myChart" style="width: 100%;height: 100%;display: flex;justify-content: center;align-items: center;"></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                chart: null,
                data1: [],
                data2: [],
            };
        },
        methods: {
            /**
             * 时间戳 => str
             * @param {时间戳} timestamp
             * @returns
             */
            timestamp2Str(timestamp) {
                var date = new Date(timestamp);
                var Y = date.getFullYear() + "-";
                var M =
                    (date.getMonth() + 1 < 10
                        ? "0" + (date.getMonth() + 1)
                        : date.getMonth() + 1) + "-";
                var D = this.fill0(date.getDate()) + " ";
                var h = this.fill0(date.getHours()) + ":";
                var m = this.fill0(date.getMinutes()) + ":";
                var s = this.fill0(date.getSeconds());
                return Y + M + D + h + m + s;
            },

            /**
             * 个位数则填充零
             * @param {数字} num
             * @returns
             */
            fill0(num) {
                return (num < 10 ? ("0" + num) : (num + ''));
            },
            getChart() {
                let that = this;
                if (that.chart === null) {
                    that.chart = that.$echarts.init(document.getElementById("myChart"));
                }
                that.chart.setOption({
                    xAxis: {
                        type: 'category',
                        data: this.data1
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            data: this.data2,
                            type: 'bar',
                            showBackground: true,
                            backgroundStyle: {
                                color: 'rgba(180, 180, 180, 0.2)'
                            }
                        }
                    ]
                });
            }
        },
        mounted() {
            this.$http.getAllData().then(res => {
                this.data1 = res.data.courses;
                this.data2 = res.data.rates;
                this.$nextTick(() => {
                    this.getChart();
                });
            });
                this.$nextTick(() => {
                    this.getChart();
                });
        },
    };
</script>
<style scoped>
    /deep/ .el-select-dropdown__item span {
        font-size: 12px;
    }

    /deep/ .el-form-item__label {
        font-size: 12px;
        letter-spacing: 1px;
    }

    /deep/ .el-input__inner {
        height: 34px;
    }

    /deep/ .el-form-item {
        margin-bottom: 25px;
    }

    /deep/ .el-input__inner {
        font-size: 12px;
    }

    .main {
        height: 100%;
        width: 100%;
        background-color: rgb(255,255,255);
        /* border: 2px solid red; */
    }

    .search {
        flex: 2;
    }

    .container {
        height: 100%;
        /* border: 2px solid red; */
        display: flex;
        width: 100%;
        flex-direction: column;
    }

    .table {
        flex: 15;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 98%;
        margin: auto;
    }

    /deep/ .el-table {
        background-color: rgba(243, 245, 237, 0) !important;
        font-size: 12px;
        font-weight: 400;
        letter-spacing: 1px;
        margin: auto;
    }

    /deep/ .el-table tr {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-table th {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-tooltip__popper {
        border: 1px solid red;
    }

    .bottom {
        flex: 0.5;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    /deep/ .el-pagination {
        background-color: rgba(243, 245, 237, 0) !important;
        margin: auto;
    }

    /deep/ .el-pagination ul {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-pagination li {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-pagination button {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    .avatar-uploader-icon {
        border: 1px dashed rgb(170, 163, 163);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    ::v-deep ::-webkit-scrollbar {
        width: 0;
        height: 0;
    }

    .avatar-uploader-icon:hover {
        border-color: #409eff;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;
    }

    .avatar {
        width: 100px;
        height: 100px;
        display: block;
        border-radius: 5px;
    }
</style>

