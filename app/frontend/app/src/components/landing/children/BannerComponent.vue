<template>

  <div class="content">

    <div class="hero-container">

      <div class="description-container">
        <h1 class="hero-title">Find Your Perfect European Getaway</h1>
        <div class="hero-description-container">
          <p class="hero-description">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Abconsectetur consequatur exercitationem nemo quo.</p>
        </div>
        <ul>
          <li>Hotel</li>
          <li>Lodge</li>
          <li>Cabin</li>
          <li>Chalet</li>
          <li>Resort</li>
        </ul>
      </div>

      <div class="wrapper">
        <button class="go-to-accommodations-button" @click="goToAccommodations">Book now</button>
      </div>

      <div class="expand-container">
        <span class="material-symbols-outlined">expand_more</span>
      </div>

    </div>

  </div>

</template>

<script>

import { animate, inView, stagger } from "motion";

export default {
  name: "BannerComponent",

  methods: {
    goToAccommodations() {
      this.$router.push('/accommodations');
    }
  },

  mounted() {
    inView('.hero-container', ({ target }) => {

      const button = target.querySelector('.go-to-accommodations-button');
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
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 90svh;
  width: 100%;
  background: var(--color-black);
}

.content:before {
  content: "";
  background-image: url("../../../../../../../../vacation-app/app/frontend/app/src/assets/hero-img.jpg");
  background-size: cover;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  opacity: 0.25;
}

.hero-container {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  width: 85svw;
}

.description-container {
  position: relative;
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 30%;
}

.hero-title {
  position: relative;
  font-size: 2.5em;
  font-weight: 700;
  line-height: 110%;
  width: 80%;
  margin-bottom: 1rem;
  color: var(--color-white);
}

.hero-description-container {
  opacity: .8;
}

.hero-description {
  color: var(--color-white);
  font-size: 1em;
  font-weight: 300;
}

ul {
  display: flex;
  gap: 2rem;
  margin-top: 2rem;
  opacity: .8;
  list-style: none;
}

li {
  display: inline;
  color: var(--color-white);
  font-size: .85em;
  font-weight: 300;
}

.wrapper {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.go-to-accommodations-button {
  background: none;
  height: 80px;
  width: 250px;
  border: none;
  outline: 2px solid var(--color-lightgrey);
  border-radius: 10px;
  font-size: 1em;
  font-weight: 600;
  margin-top: 5rem;
  color: var(--color-white);
  backdrop-filter: blur(1px);
  transition: backdrop-filter 0.3s ease;
  cursor: pointer;
}

.go-to-accommodations-button:hover {
  backdrop-filter: blur(10px);
}

.expand-container {
  position: absolute;
  bottom: 3%;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  opacity: 50%;
  animation: arrow 4s infinite;
}

@keyframes arrow {
  0% {
    transform: translateY(0);
  }
  10% {
    transform: translateY(10px);
  }
  20% {
    transform: translateY(0);
  }
  30% {
    transform: translateY(10px);
  }
  40% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(0);
  }
}

.material-symbols-outlined {
  color: var(--color-white);
  transform: scale(1.5);
  font-variation-settings:
     'FILL' 0,
     'wght' 400,
     'GRAD' 0,
     'opsz' 24
}

@media only screen and (min-width: 1400px) {

  .hero-title {
    font-size: 2em;
    font-weight: 700;
    width: 100%;
  }

  .description-container {
    width: 32%;
  }

  .hero-description {
    font-size: .85em;
    font-weight: 300;
  }

  .go-to-accommodations-button {
    height: 65px;
  }

}

</style>