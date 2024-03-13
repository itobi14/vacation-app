<template>

  <div class="content">

    <div class="hero-container">

      <div class="description-container">
        <h1 class="hero-title">Find Your Perfect European Getaway</h1>
        <p class="hero-description">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Abconsectetur consequatur exercitationem nemo quo repellendus bedellus.</p>
        <ul>
          <li>Hotel</li>
          <li>Lodge</li>
          <li>Cabin</li>
          <li>Safari</li>
          <li>Inns</li>
        </ul>
      </div>

      <div class="wrapper">
        <button class="go-to-assortment" @click="goToAccommodations">Book now</button>
      </div>

    </div>

  </div>

</template>

<script>

import { animate, inView, stagger } from "motion";

export default {
  name: "BannerComponent",

  data() {
    return {

    }
  },

  methods: {
    goToAccommodations() {
      this.$router.push('/accommodations');
    }
  },

  mounted() {
    inView('.hero-container', ({ target }) => {

      const button = target.querySelector('.go-to-assortment');
      const title = target.querySelector('.hero-title');
      const description = target.querySelector('.hero-description');
      const features = target.querySelectorAll('li');

      animate(button, {
        opacity: [0, 1],
        transform: ['scale(95%)', 'scale(100%)'],
      }, { delay: (1.2), duration: 0.3, easing: ["ease-in"]});

      animate(title, {
        opacity: [0, 1],
      }, { duration: 0.5, easing: ["ease-in"] });

      animate(description, {
        opacity: [0, 1],
      }, { duration: 0.5, easing: ["ease-in"] });

      animate(features, {
        opacity: [0, 1],
        transform: ['translateX(-100%)', 'translateX(0)'],
      }, { delay: stagger(0.3), easing: ["ease-in"] });


    });
  },

}
</script>

<style scoped>

.content {
  width: 85svw;
  height: auto;
}

.hero-container {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 750px;
  width: 100%;
  border-radius: 0 50px 0 50px;
  background: var(--black);
}

.hero-container::before {
  content: "";
  background-image: url("../../../../../../../../vacation-app/app/frontend/app/src/assets/hero-img.jpg");
  background-size: cover;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  border-radius: 0 50px 0 50px;
  opacity: 0.3;
}

.description-container {
  position: relative;
  display: flex;
  justify-content: center;
  flex-direction: column;
  //background: lightgreen;
  width: 50%;
  padding: 0 200px;
}

.hero-title {
  position: relative;
  font-size: 40px;
  font-weight: 700;
  line-height: 110%;
  margin-bottom: 12px;
  color: var(--white);
}

/*
.hero-title::before {
  content: '';
  position: absolute;
  bottom: 0;
  right: 8%;
  width: 10px;
  height: 10px;
  background: #5daab5;
}
*/

.hero-description {
  color: var(--white);
  font-size: 14px;
  font-weight: 300;
}

ul {
  display: flex;
  gap: 2rem;
  margin-top: 2rem;
  list-style: none;
}

li {
  display: inline;
  color: #ccc;
  font-size: 15px;
  font-weight: 500;
}

.wrapper {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.go-to-assortment {
  background: none;
  height: 80px;
  width: 250px;
  border: none;
  outline: 2px solid #ccc;
  border-radius: 10px;
  font-size: 20px;
  font-weight: 600;
  margin-top: 4rem;
  color: var(--white);
  backdrop-filter: blur(1px);
  transition: backdrop-filter 0.3s ease;
  cursor: pointer;
}

.go-to-assortment:hover {
  backdrop-filter: blur(10px);
}

</style>