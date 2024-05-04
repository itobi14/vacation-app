<template>

  <app-navbar></app-navbar>
  <div class="app-view"><router-view></router-view></div>
  <app-footer></app-footer>

</template>

<script>

import NavBarComponent from "@/components/NavBarComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
import {AccountsAdaptor} from "@/services/accounts-adaptor";
import {AccommodationsAdaptor} from "@/services/accommodations-adaptor";
import {SessionService} from "@/services/session-service";
import CONFIG from '@/app-config.js'
import {shallowReactive} from "vue";

export default {

  name: 'App',
  components: {
    'app-navbar': NavBarComponent,
    'app-footer': FooterComponent,
  },

  created() {
    // console.log('Configured routerGuard');
    this.$router.beforeEach(this.routerGuard);
  },

  provide() {
    this.theSessionService = shallowReactive(
        new SessionService(CONFIG.BACKEND_URL + "/authentication", CONFIG.JWT_STORAGE_ITEM));
    return {
      accountsService: new AccountsAdaptor(CONFIG.BACKEND_URL+"/accounts"),
      accommodationsService: new AccommodationsAdaptor(CONFIG.BACKEND_URL+"/accommodations"),
      sessionService: this.theSessionService
    }
  },

  methods: {
    routerGuard(to,from) {
      if (to.name === 'PROFILE') {
        if (!this.theSessionService.isAuthenticated()) {
          console.log("Intercepted route from '" + from.name + "' to '" + to.name + "'");
          return '/unauthorised';
        }
      }
    }
  },

}

</script>

<style scoped>

</style>