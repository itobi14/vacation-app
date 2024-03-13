<template>

  <banner-component></banner-component>
  <deals-component></deals-component>
  <reviews-component></reviews-component>

</template>

<script>

import BannerComponent from "@/components/hero/components/BannerComponent.vue";
import ReviewsComponent from "@/components/hero/components/ReviewsComponent.vue";
import InfoComponent from "@/components/hero/components/InfoComponent.vue";

export default {
  name: "HeroComponent",
  components: {
    'banner-component': BannerComponent,
    'deals-component': InfoComponent,
    'reviews-component': ReviewsComponent,
  },

  data() {
    return {

    }
  },

  methods: {

  },

  mounted() {

  },

}
</script>

<style scoped>

</style>