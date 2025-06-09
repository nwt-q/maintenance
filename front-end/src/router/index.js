import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: () => import("../views/Login.vue"),
  },
  {
    path: "/MangeIndex",
    name: "MangeIndex",
    component: () => import("../views/MangeIndex.vue"),
    children: [
      {
        path: "",
        name: "ManageEquipmentCondition",
        component: () => import("../views/ManageEquipmentCondition.vue"),
      },
      {
        path: "ManageUserCondition",
        name: "ManageUserCondition",
        component: () => import("../views/ManageUserCondition.vue"),
      },
      {
        path: "ManageMaintenanceCondition",
        name: "ManageMaintenanceCondition",
        component: () => import("../views/ManageMaintenanceCondition.vue"),
      },
      {
        path: "ManageCategoryCondition",
        name: "ManageCategoryCondition",
        component: () => import("../views/ManageCategoryCondition.vue"),
      },
      {
        path: "ManageRecordCondition",
        name: "ManageRecordCondition",
        component: () => import("../views/ManageRecordCondition.vue"),
      },
      {
        path: "Detail/:data?",
        name: "Detail",
        component: () => import("../views/Detail.vue"),
      },
      {
        path: "statistics",
        name: "statistics",
        component: () => import("../views/statistics.vue"),
      },
    ],
  },
  {
    path: "/StudentIndex",
    name: "StudentIndex",
    component: () => import("../views/StudentIndex.vue"),
    children: [
      {
        path: "",
        name: "StudentEquipmentCondition",
        component: () => import("../views/StudentEquipmentCondition.vue"),
      },
      {
        path: "StudentRecordCondition",
        name: "StudentRecordCondition",
        component: () => import("../views/StudentRecordCondition.vue"),
      },
      {
        path: "Mine",
        name: "Mine",
        component: () => import("../views/Mine.vue"),
      },
    ],
  },

  {
    path: "/MaintenanceIndex",
    name: "MaintenanceIndex",
    component: () => import("../views/MaintenanceIndex.vue"),
    children: [
      {
        path: "",
        name: "Todo",
        component: () => import("../views/Todo.vue"),
      },
      {
        path: 'MaintenanceKnowledgeBase',
        name: 'MaintenanceKnowledgeBase',
        component: () => import('../views/MaintenanceKnowledgeBase.vue'),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
