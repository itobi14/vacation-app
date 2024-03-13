<template>

  <div class="hero-component">
    <banner-component></banner-component>
    <info-component></info-component>
    <reviews-component></reviews-component>
  </div>

</template>

<script>

import BannerComponent from "@/components/hero/components/BannerComponent.vue";
import ReviewsComponent from "@/components/hero/components/ReviewsComponent.vue";
import InfoComponent from "@/components/hero/components/InfoComponent.vue";

export default {
  name: "HeroComponent",
  components: {
    'banner-component': BannerComponent,
    'info-component': InfoComponent,
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

.hero-component {
  display: flex;
  flex-direction: column;
}

</style>