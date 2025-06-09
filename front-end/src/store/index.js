import Vue from 'vue'
import Vuex from 'vuex'
import persistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentUser: null,
    token: '',
    detailId:'',
  },
  mutations: {
    setCurrentUser(state,user) {
      state.currentUser = user;
    },
    setToken(state,token) {
      state.token = token;
    },
    setDetailId(state,detailId){
      state.detailId = detailId;
    },
  },
  actions: {
  },
  modules: {
  },
  plugins: [
    persistedState({ storage: window.sessionStorage })
  ]
})
