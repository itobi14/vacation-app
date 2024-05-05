<template>

  <div class="hero-component">
    <banner-component></banner-component>
    <info-component></info-component>
    <reviews-component></reviews-component>
  </div>

</template>

<script>

import BannerComponent from "@/components/landing/children/BannerComponent.vue";
import ReviewsComponent from "@/components/landing/children/ReviewsComponent.vue";
import InfoComponent from "@/components/landing/children/InfoComponent.vue";

export default {
  name: "HeroComponent",
  components: {
    'banner-component': BannerComponent,
    'info-component': InfoComponent,
    'reviews-component': ReviewsComponent,
  },

}
</script>

<style scoped>

.hero-component {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 100%;
}

</style>