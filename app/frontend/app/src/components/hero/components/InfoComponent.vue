<template>

  <div class="info-content">

    <h1 class="title">How does it work?</h1>

    <div class="container-wrapper" ref="containerWrapper">

      <div class="progress-indicator"></div>

      <div class="container">

      </div>

      <div class="progress-indicator"></div>

      <div class="container">

      </div>

      <div class="progress-indicator"></div>

      <div class="container">

      </div>

    </div>

  </div>

</template>

<script>

// import {animate, stagger} from "motion";

export default {
  name: "InfoComponent",

  // mounted() {
  //   // Create options for the Intersection Observer
  //   const options = {
  //     root: null, // Observe relative to the viewport
  //     threshold: 0.5 // Element is considered in view when 50% visible
  //   };
  //
  //   // Define the callback function for intersection changes
  //   const handleIntersection = (entries) => {
  //     entries.forEach(entry => {
  //       if (entry.isIntersecting && this.$refs.containerWrapper) {
  //         this.animation();
  //       }
  //     });
  //   };
  //
  //   // Create the Intersection Observer instance
  //   this.observer = new IntersectionObserver(handleIntersection, options);
  //
  //   // Observe the container-wrapper element
  //   this.observer.observe(this.$refs.containerWrapper);
  // },
  //
  // methods: {
  //   animation() {
  //     const target = document.values.querySelector('.container-wrapper')
  //     const indicator = target.values.querySelectorAll('.progress-indicator')
  //     animate(indicator, {
  //       opacity: [0, 1],
  //       transform: ['translateX(-100%)', 'translateX(0)'],
  //     }, { delay: stagger(0.3), easing: ["ease-in"] });
  //   }
  // },
  //
  // beforeUnmount() {
  //   // Disconnect the observer on component unmount (optional)
  //   if (this.observer) {
  //     this.observer.disconnect();
  //   }
  // },

}

</script>

<style scoped>

.info-content {
  //background: lightblue;
  width: 85svw;
  height: auto;
  padding: 5rem;
}

.title {
  font-size: 30px;
  font-weight: 700;
  color: var(--black);
  margin-bottom: 3rem;
}

.container-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  //background: lightgreen;
}

.progress-indicator {
  height: 10px;
  width: 75px;
  border-radius: 50px;
  background: #e5e5e5;
}

.container {
  height: 500px;
  width: 300px;
  border-radius: 10px;
  box-shadow: rgba(149, 157, 165, 0.2) 0 8px 24px;
  background: var(--white);
}

</style>