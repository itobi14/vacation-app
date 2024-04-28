<template>

  <div class="content" v-if="accommodation">
    <img :src="getImgUrl(accommodation.imgUrl)" class="accommodation-img" alt="Accommodation Image">

    <div class="main">
      <div class="main-info-container">

        <div class="header">
          <h2 class="accommodation-name">{{ accommodation.name }}</h2>
          <div v-if="isAuthenticated" class="favorite-container" @click="toggleFavorite(accommodation, accommodation.id)">
            <div v-if="isFavorite">
              <span class="material-symbols-outlined favorite filled">favorite</span>
            </div>
            <div v-if="!isFavorite">
              <span class="material-symbols-outlined favorite">favorite</span>
            </div>
          </div>
        </div>

        <div class="info-wrapper">
          <div class="accommodation-country-city-wrapper">
            <span class="material-symbols-outlined map">map</span>
            <p class="accommodation-country-city">{{ accommodation.country }},&nbsp;</p>
            <p class="accommodation-country-city">{{ accommodation.city }}</p>
          </div>
          <div class="accommodation-address-container">
            <span class="material-symbols-outlined location">location_on</span>
            <p class="accommodation-address">{{ accommodation.streetNameNr }},&nbsp;</p>
            <p class="accommodation-address">{{ accommodation.zipCode }}</p>
          </div>

          <span class="accommodation-price-bold">&euro; {{ accommodation.price }} per night</span>
        </div>

        <div class="description-container">
          <p class="accommodation-description">{{ accommodation.description }}</p>
        </div>

      </div>

        <div class="book-container">
          <div class="title-container">
            <h2 class="title">Book your stay!</h2>
            <img src=".././assets/ticket-1.svg" class="book-logo-container" alt="Logo">
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
            <p class="total-price">&euro; {{ totalPrice }}</p>
          </div>
          <button class="book-button"
                  :disabled="isBookButtonDisabled"
                  @click="openBookingConfirmModal">Book</button>
        </div>

    </div>

  </div>

  <!-- Booking confirm modal -->
  <BookingConfirmComponent
      v-if="showBookingConfirmModal"
      :accommodation="accommodation"
      :startDate="startDate"
      :endDate="endDate"
      :totalPrice="totalPrice"
      @close-modal="closeBookingConfirmModal"
      @confirm-booking="confirmBooking">
  </BookingConfirmComponent>

</template>

<script>

import BookingConfirmComponent from "@/components/BookingConfirmComponent.vue";

export default {
  name: "AccommodationDetailComponent",
  components: { BookingConfirmComponent },
  inject: ['accountsService', 'accommodationsService', 'sessionService'],

  data() {
    return {
      accommodation: null,
      isFavorite: false,
      startDate: null,
      endDate: null,
      selectedNights: 0,
      totalPrice: 0,
      datesSelected: false,
      minDate: this.getMinDate(),
      showBookingConfirmModal: false
    };
  },

  async created() {
    await this.fetchAccommodation()
    await this.fetchFavorite()
  },

  computed: {
    isBookButtonDisabled() {
      return !this.startDate || !this.endDate;
    },
    isAuthenticated() {
      // console.log("isAuthenticated=",this.sessionService.isAuthenticated());
      return this.sessionService.isAuthenticated();
    },
  },

  methods: {

    async fetchAccommodation() {

      const accommodationId = this.$route.params.id;

      if (accommodationId !== undefined) {
        this.accommodation = await this.accommodationsService.findById(accommodationId);
        // console.log("Selected accommodation:", this.accommodation);
      } else {
        console.error("ID is undefined");
      }
    },

    async fetchFavorite() {

      if (this.isAuthenticated) {

        const accountId = this.sessionService.currentAccount.id;
        const accommodationId = this.accommodation.id

        if (accountId !== undefined) {
          const isFavorite = await this.accountsService.findFavorite(accountId, accommodationId);
          if (isFavorite) {
            this.isFavorite = true;
          }
        }
      }

    },

    async toggleFavorite(accommodation, accommodationId) {

      if (this.isAuthenticated) {
        try {
          const accountId = this.sessionService.currentAccount.id;

          if (this.isFavorite) {
            await this.accountsService.removeFavorite(accountId, accommodationId);
            this.isFavorite = false;
          } else {
            await this.accountsService.addFavorite(accountId, accommodationId);
            this.isFavorite = true;
          }
        } catch (error) {
          console.error('Error toggling favorite:', error);
        }
      }
    },

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

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/placeholder.svg');
      }
    },

    openBookingConfirmModal() {
      this.showBookingConfirmModal = true;
    },

    closeBookingConfirmModal() {
      this.showBookingConfirmModal = false;
    },

    confirmBooking() {
      // Implement logic to confirm booking (e.g., send request to backend)
      console.log("Booking confirmed!");
      // Close the modal
      this.closeBookingConfirmModal();
    },

  },

}
</script>

<style scoped>

.content {
  display: flex;
  flex-direction: column;
  width: 60svw;
  height: auto;
  //background: lightskyblue;
}

.accommodation-img {
  height: 500px;
  width: 100%;
  border-radius: 10px;
  object-fit: cover;
}

.main {
  display: flex;
  //background: lightblue;
  padding: 1rem 0;
}

.main-info-container {
  width: 60%;
  padding: 0 5rem 0 0;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  //background: #5daab5;
}

.accommodation-name {
  font-size: 25px;
  font-weight: 700;
  color: var(--black);
}

.favorite-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 30px;
  width: 30px;
  //background: #35818a;
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
  margin-top: .5rem;
  display: flex;
  flex-direction: column;
  gap: .3rem;
  padding-bottom: 1rem;
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
  width: 40%;
  padding: 1rem;
  border-radius: 10px;
  border: 1px solid #eee;
}

.title-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 8px;
  border-bottom: 1px solid #eee;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: var(--black);
}

.book-logo-container {
  height: 40px;
  width: 40px;
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
  margin-top: 1rem;
  font-size: 16px;
  font-weight: 500;
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
  border-top: 2px solid #F5F5F5;
  padding: 1rem 0;
}

.material-symbols-outlined.favorite {
  color: var(--black);
  margin-top: .4rem;
  transform: scale(1.2);
  cursor: pointer;
  font-variation-settings:
      'FILL' 0,
      'wght' 400,
      'GRAD' 0,
      'opsz' 0
}

.material-symbols-outlined.favorite.filled {
  color: var(--black);
  margin-top: .4rem;
  transform: scale(1.2);
  cursor: pointer;
  font-variation-settings:
      'FILL' 1,
      'wght' 400,
      'GRAD' 0,
      'opsz' 0
}

</style>