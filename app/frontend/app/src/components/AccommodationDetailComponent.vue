<template>

  <div class="content" v-if="accommodation">
    <img :src="accommodation.imgUrl" class="accommodation-img" alt="Accommodation Image">

    <div class="main">
      <div class="main-info-container">
        <h2 class="accommodation-name">{{ accommodation.name }}</h2>
        <div class="accommodation-country-city-wrapper">
          <span class="material-symbols-outlined map">map</span>
          <p class="accommodation-country-city">{{ accommodation.country }},&nbsp;</p>
          <p class="accommodation-country-city">{{ accommodation.city }}</p>
        </div>

        <div class="info-wrapper">
          <div class="accommodation-address-container">
            <span class="material-symbols-outlined location">location_on</span>
            <p class="accommodation-address">{{ accommodation.streetNameNr }},&nbsp;</p>
            <p class="accommodation-address">{{ accommodation.zipCode }}</p>
          </div>

          <span class="accommodation-price-bold">€ {{ accommodation.price }}
            <span class="accommodation-price-regular">per night</span>
          </span>
        </div>

      </div>

        <div class="book-container">
          <div class="title-container">
            <h2 class="title">Book your stay!</h2>
            <img src=".././assets/logo.svg" class="logo-container" alt="Logo">
          </div>
          <div v-if="!datesSelected" class="date-title-wrapper">
            <span class="material-symbols-outlined explore">travel_explore</span>
            <div class="ww">
              <p class="date-title">Ready to travel?</p>
              <p class="date-title">Please pick your check-in and checkout dates</p>
            </div>
          </div>
          <div class="date-wrapper">
            <div class="date-container">
              <div class="date-picker-wrapper">
                <label for="check-in" class="check-text">Check-in</label>
                <input type="date" class="date-picker" v-model="startDate" :min="minDate" @change="calculatePrice">
              </div>
              <span class="material-symbols-outlined">calendar_today</span>
            </div>
            <div class="date-container">
              <div class="date-picker-wrapper">
                <label for="checkout" class="check-text">Checkout</label>
                <input type="date" class="date-picker" v-model="endDate" :min="minDate" @change="calculatePrice">
              </div>
              <span class="material-symbols-outlined">calendar_today</span>
            </div>
          </div>
          <div class="total-wrapper">
            <p class="total-price-text">Total</p>
            <p class="total-price">€&nbsp;{{ totalPrice }}</p>
          </div>
          <button class="book-button" :disabled="isBookButtonDisabled">Book</button>
        </div>

    </div>

    <div class="description-container">
      <p class="accommodation-description">{{ accommodation.description }}</p>
    </div>

  </div>

</template>

<script>
export default {
  name: "AccommodationDetailComponent",
  inject: ['accommodationsService', 'sessionService'],

  data() {
    return {
      accommodation: null,
      startDate: null,
      endDate: null,
      selectedNights: 0,
      totalPrice: 0,
      datesSelected: false,
      minDate: this.getMinDate(),
    };
  },

  async created() {
    // Access the id from $route.params
    const id = this.$route.params.id;

    // Check if id is defined
    if (id !== undefined) {
      // Fetch accommodation details using the id
      this.accommodation = await this.accommodationsService.findById(id);
      console.log("Selected accommodation:", this.accommodation);
    } else {
      console.error("ID is undefined");
      // Handle the case where id is not defined
    }
  },

  computed: {
    isBookButtonDisabled() {
      return !this.startDate || !this.endDate;
    }
  },

  methods: {
    calculatePrice() {
      if (this.startDate && this.endDate) {

        this.datesSelected = true

        const start = new Date(this.startDate);
        const end = new Date(this.endDate);
        const timeDiff = Math.abs(end.getTime() - start.getTime());
        const nightCount = Math.ceil(timeDiff / (1000 * 3600 * 24));
        this.selectedNights = nightCount;
        this.totalPrice = this.accommodation.price * nightCount;
      }
    },

    getMinDate() {
      const today = new Date();
      const year = today.getFullYear();
      let month = today.getMonth() + 1;
      let day = today.getDate();

      month = month < 10 ? '0' + month : month;
      day = day < 10 ? '0' + day : day;

      return `${year}-${month}-${day}`;
    },

  },

}
</script>

<style scoped>

.content {
  display: flex;
  flex-direction: column;
  //background: #f5f5f5;
  width: auto;
  height: auto;
  //background: lightskyblue;
}

.accommodation-img {
  height: 500px;
  width: 1000px;
  background-size: cover;
  background: center;
  border-radius: 15px;
  background: lightseagreen;
}

.main {
  display: flex;
  justify-content: space-between;
  //background: lightblue;
  border-bottom: 2px solid #eee;
  padding: 1rem 0;
}

.accommodation-name {
  font-size: 25px;
  font-weight: 700;
  color: var(--black);
}

.accommodation-country-city-wrapper {
  display: flex;
  align-items: center;
}

.material-symbols-outlined.location,
.material-symbols-outlined.map {
  color: var(--black);
  margin-right: 10px;
  font-variation-settings:
      'FILL' 0,
      'wght' 500,
      'GRAD' 0,
      'opsz' 24
}

.info-wrapper {
  display: flex;
  flex-direction: column;
  margin-top: 2rem;
  //background: #b43e3c;
}

.accommodation-country-city {
  font-size: 16px;
  font-weight: 400;
  color: #222;
}

.accommodation-address-container {
  display: flex;
  align-items: center;
}

.accommodation-address {
  font-size: 14px;
  font-weight: 400;
  color: var(--black);
}

/* ------------------------------------------------------------------------------------------------------------------ */

.book-container {
  display: flex;
  flex-direction: column;
  padding: 1rem;
  border-radius: 10px;
  border: 1px solid #eee;
}

.title-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #eee;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: var(--black);
}

.logo-container {
  height: 50px;
  width: 50px;
}

.date-title-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px;
  margin-top: 1rem;
  background: #e2eeef;
  border: 2px solid #5daab5;
  border-radius: 5px;
}

.material-symbols-outlined.explore {
  color: #35818a;
  font-variation-settings:
      'FILL' 0,
      'wght' 500,
      'GRAD' 0,
      'opsz' 20
}

.date-title {
  font-size: 14px;
  font-weight: 400;
  width: 100%;
  color: #35818a;
}

.accommodation-price-bold {
  font-size: 16px;
  font-weight: 600;
  color: var(--black);
}

.accommodation-price-regular {
  font-size: 16px;
  font-weight: 400;
  color: #555;
  margin-left: 6px;
}

.date-wrapper {
  display: flex;
  gap: 10px;
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.date-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 5px;
  width: 200px;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #555;
}

.date-picker-wrapper {
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 2px;
  width: 100%;
}

.material-symbols-outlined {
   transform: scale(0.8);
   font-variation-settings:
       'FILL' 0,
       'wght' 400,
       'GRAD' 0,
       'opsz' 20
}

.check-text {
  font-size: 12px;
  font-weight: 600;
  color: var(--black);
  text-transform: uppercase;
}

.date-picker {
  position: relative;
  border: none;
  outline: none;
  color: #555;
  width: 100%;
}

.date-picker::-webkit-calendar-picker-indicator {
  opacity: 0;
  position: absolute;
  height: 100%;
  width: 100%;
  cursor: pointer;
}

.book-button {
  background: #5daab5;
  color: var(--white);
  font-size: 16px;
  font-weight: 600;
  margin-top: 1rem;
  padding: 1rem 6rem;
  border-radius: 50px;
  border: none;
  outline: none;
  cursor: pointer;
}

.book-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.total-wrapper {
  display: flex;
  justify-content: space-between;
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.total-price-text {
  font-size: 16px;
  font-weight: 600;
}

.total-price {
  font-size: 16px;
  font-weight: 600;
}

.description-container {
  padding: 1rem 0;
}

</style>