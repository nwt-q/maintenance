import { MessageBox, Message,Notification } from 'element-ui';


export default {
    confirm(message) {
        return new Promise((resoleve, reject) => {
            MessageBox.confirm(
                message,
                '提示',
                {
                    confirmButtonText: '确定',
                    cancelButtonText: "取消",
                    dangerouslyUseHTMLString: true,
                    type: "warning",
                }
            ).then(() => {
                resoleve(true);
            }).catch(() => {
                Message.info('已取消操作');
                reject(false);
            })
        }).catch((err) => {
            console.log(err);
        })
    },
    message: {
        success(message) {
            Message.success(message);
        },
        error(message) {
            Message.error(message);
        },
        warning(message) {
            Message.warning(message);
        },
        info(message) {
            Message.info(message);
        }
    },
    notify: {
        success(message) {
            Notification({
                title: '成功',
                message,
                type: 'success',
                duration: 3000,
            })
        },
        error(message) {
            Notification({
                title: '错误',
                message,
                type: 'error',
                duration: 5000,
            })
        },
        warning(message) {
            Notification({
                title: '警告',
                message,
                type: 'warning',
                duration: 4000,
            })
        },
        info(message) {
            Notification({
                title: '提示',
                message,
                type: 'info',
                duration: 3000,
            })
        }
    }
 }