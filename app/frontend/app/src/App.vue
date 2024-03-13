<template>

  <app-navbar></app-navbar>
  <div class="app-view"><router-view></router-view></div>
  <app-footer></app-footer>

</template>

<script>

import NavBarComponent from "@/components/NavBarComponent.vue";
import {AccountsAdaptor} from "@/services/accounts-adaptor";
import CONFIG from '@/app-config.js'
import {shallowReactive} from "vue";
import {SessionService} from "@/services/session-service";
import FooterComponent from "@/components/FooterComponent.vue";

export default {

  name: 'App',
  components: {
    'app-navbar': NavBarComponent,
    'app-footer': FooterComponent,
  },

  created() {
    console.log('Configured routerGuard');
    this.$router.beforeEach(this.routerGuard);
  },

  provide() {
    // TODO Use a dynamic (hashed) JWT_STORAGE_ITEM name for greater security
    this.theSessionService = shallowReactive(
        new SessionService(CONFIG.BACKEND_URL + "/authentication", CONFIG.JWT_STORAGE_ITEM));
    // this.theFetchInterceptor =
    //     new FetchInterceptor(this.theSessionService, this.$router);
    return {
      // stateless data services adaptor singletons
      accountsService: new AccountsAdaptor(CONFIG.BACKEND_URL+"/accounts"),

      // reactive, state-full services
      sessionService: this.theSessionService
    }
  },

  beforeUnmount() {
    // this.theFetchInterceptor.unregister();
  },

  methods: {
    routerGuard(to,from) {
      // global routing guards set up in order to be able to query theSessionService status
      if (to.name === 'ADMIN') {
        if (!this.theSessionService.isAdmin()) {
          console.log("Intercepted route from '" + from.name + "' to '" + to.name + "'");
          return '/unauthorised';
        }
      } else if (to.name === 'ORDERS') {
        if (!this.theSessionService.isAuthenticated()) {
          console.log("Intercepted route from '" + from.name + "' to '" + to.name + "'");
          return { name: 'SIGN-IN', query: { targetRoute: to.name, cancelRoute: 'HOME' }};
        }
      }
    }
  },

}

</script>

<style scoped>

</style>