<template>

  <banner-component></banner-component>
  <deals-component></deals-component>
  <reviews-component></reviews-component>

</template>

<script>

import BannerComponent from "@/components/Hero/components/BannerComponent.vue";
import DealsComponent from "@/components/Hero/components/DealsComponent.vue";
import ReviewsComponent from "@/components/Hero/components/ReviewsComponent.vue";

export default {
  name: "HeroComponent",
  components: {
    'banner-component': BannerComponent,
    'deals-component': DealsComponent,
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