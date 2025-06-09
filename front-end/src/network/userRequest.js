import { request } from '../common/request'

export default {
    doLogin(mobile, password) {
        return request({
            url: "/doLogin",
            method: 'post',
            params: {
                mobile,
                password
            }
        })
    },

    uploadFile(file) {
        let formData = new FormData();
        formData.append("file", file)
        return request({
            url: "/uploadFile",
            method: 'post',
            data: formData
        })
    },


    viewUserTable(username) {
        return request({
            url: "/viewUserTable",
            method: 'get',
            params: {
                username,
            }
        })
    },

    viewEquipment(id) {
        return request({
            url: "/viewEquipment",
            method: 'get',
            params: {
                id,
            }
        })
    },

    deleteUser(id) {
        return request({
            url: "/deleteUser",
            method: 'post',
            params: {
                id
            }
        })
    },

    sendTextMailMessage(id) {
        return request({
            url: "/sendTextMailMessage",
            method: 'post',
            params: {
                id
            }
        })
    },

    updateUser(user) {
        return request({
            url: "/updateUser",
            method: 'post',
            data: user,
        })
    },


    createUser(user) {
        return request({
            url: "/createUser",
            method: 'post',
            data: user,
        })
    },


    viewRecordTable(equipmentName, categoryName, userId, equipmentId) {
        return request({
            url: "/viewRecordTable",
            method: 'get',
            params: {
                equipmentName,
                categoryName,
                userId,
                equipmentId
            }
        })
    },


    viewRecord(equipmentId) {
        return request({
            url: "/viewRecord",
            method: 'get',
            params: {
                equipmentId
            }
        })
    },
    deleteRecord(id) {
        return request({
            url: "/deleteRecord",
            method: 'post',
            params: {
                id
            }
        })
    },


    updateRecord(record) {
        return request({
            url: "/updateRecord",
            method: 'post',
            data: record,
        })
    },
    updateRecord2(record) {
        return request({
            url: "/updateRecord2",
            method: 'post',
            data: record,
        })
    },

    createRecord(record) {
        return request({
            url: "/createRecord",
            method: 'post',
            data: record,
        })
    },


    viewMaintenanceTable(username, mobile, mailbox) {
        return request({
            url: "/viewMaintenanceTable",
            method: 'get',
            params: {
                username,
                mobile,
                mailbox,
            }
        })
    },

    viewMaintenanceByEquipment(ids) {
        return request({
            url: "/viewMaintenanceByEquipment",
            method: 'get',
            params: {
                ids
            }
        })
    },

    viewMaintenanceByEquipment2(id) {
        return request({
            url: "/viewMaintenanceByEquipment2",
            method: 'get',
            params: {
                id
            }
        })
    },

    getAllData() {
        return request({
            url: "/getAllData",
            method: 'post',
            params: {
            }
        })
    },

    deleteMaintenance(id) {
        return request({
            url: "/deleteMaintenance",
            method: 'post',
            params: {
                id
            }
        })
    },

    updateMaintenance(maintenance) {
        return request({
            url: "/updateMaintenance",
            method: 'post',
            data: maintenance,
        })
    },

    createMaintenance(maintenance) {
        return request({
            url: "/createMaintenance",
            method: 'post',
            data: maintenance,
        })
    },

    viewEquipmentTable(name, type, status) {
        return request({
            url: "/viewEquipmentTable",
            method: 'get',
            params: {
                name,
                type,
                status,
            }
        })
    },

    deleteEquipment(id) {
        return request({
            url: "/deleteEquipment",
            method: 'post',
            params: {
                id
            }
        })
    },

    updateEquipment(equipment) {
        return request({
            url: "/updateEquipment",
            method: 'post',
            data: equipment,
        })
    },

    updateEquipmentDetail(equipment) {
        return request({
            url: "/updateEquipmentDetail",
            method: 'post',
            data: equipment,
        })
    },

    createEquipment(equipment) {
        return request({
            url: "/createEquipment",
            method: 'post',
            data: equipment,
        })
    },

    viewCategoryTable(name) {
        return request({
            url: "/viewCategoryTable",
            method: 'get',
            params: {
                name,
            }
        })
    },

    deleteCategory(id) {
        return request({
            url: "/deleteCategory",
            method: 'post',
            params: {
                id
            }
        })
    },

    updateCategory(category) {
        return request({
            url: "/updateCategory",
            method: 'post',
            data: category,
        })
    },

    createCategory(category) {
        return request({
            url: "/createCategory",
            method: 'post',
            data: category,
        })
    },

    // 维修知识库相关API
    getKnowledgeBaseList(params) {
        return request({
            url: "/knowledgebase",
            method: 'get',
            params: params
        })
    },
    // 上传维修知识库
    uploadKnowledgeBase(formData) {
        return request({
            url: "/upload",
            method: 'post',
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    },
    // 更新维修知识库
    updateKnowledgeBase(formData) {
        return request({
            url: "/upload",
            method: 'post',
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    },
    // 删除维修知识库
    deleteKnowledgeBase(id) {
        return request({
            url: "/dknowledgebase",
            method: 'delete',
            params: {
                id
            }
        })
    },

    downloadKnowledgeBase(id) {
        return request({
            url: `/downloadPdf`,
            method: 'get',
            params: {
                path: id,
            },
            responseType: 'blob'
        })
    },

    getKnowledgeBaseDetail(id) {
        return request({
            url: "/knowledgebase",
            method: 'get',
            params: {
                id
            }
        })
    }
}