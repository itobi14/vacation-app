<template>
  <div class="backdrop">

    <div class="booking-confirm-modal">
      <div class="modal-content">
        <div class="title-container">
          <h2 class="title">Confirm Booking</h2>
        </div>

        <div class="inform-container">
          <span class="material-symbols-outlined book">import_contacts</span>
          <div class="ww">
            <p class="inform-text">Review important booking details</p>
            <p class="inform-text">Confirming initiates payment</p>
          </div>
        </div>

        <div class="info-container">
          <p class="section-title">Trip info</p>

          <div class="wrapper-trip">
            <p class="text-trip-normal">Name: </p>
            <p class="text-trip-bold">{{ accommodation.name }}</p>
          </div>
          <div class="wrapper-trip">
            <p class="text-trip-normal">City: </p>
            <p class="text-trip-bold">{{ accommodation.city }}</p>
          </div>
          <div class="wrapper-trip">
            <p class="text-trip-normal">Country: </p>
            <p class="text-trip-bold">{{ accommodation.country }}</p>
          </div>
          <div class="wrapper-trip">
            <p class="text-trip-normal">Street: </p>
            <p class="text-trip-bold">{{ accommodation.streetNameNr }}</p>
          </div>
          <div class="wrapper-trip">
            <p class="text-trip-normal">Zip code: </p>
            <p class="text-trip-bold">{{ accommodation.zipCode }}</p>
          </div>


        </div>

        <div class="info-container">
          <p class="section-title">Dates</p>
          <div class="wrapper">
            <p class="text-normal">Check-in</p>
            <p class="text-bold">{{ startDate }}</p>
          </div>
          <div class="wrapper">
            <p class="text-normal">Checkout</p>
            <p class="text-bold">{{ endDate }}</p>
          </div>
        </div>

        <div class="info-container">
          <p class="section-title">Price details</p>
          <div class="wrapper">
            <p class="text-normal">Price per night</p>
            <p class="text-bold">€{{ accommodation.price }}</p>
          </div>
          <div class="wrapper">
            <p class="text-normal">Number of nights</p>
            <p class="text-bold">x{{ totalNights }}</p>
          </div>
          <div class="wrapper-total">
            <p class="text-bold">Total</p>
            <p class="text-bold">€{{ totalPrice }}</p>
          </div>
        </div>
        <div class="buttons-container">
          <button class="confirm-button" @click="confirmBooking">Confirm</button>
          <button class="cancel-button" @click="closeModal">Cancel</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "BookingConfirmComponent",
  emits: ['close-modal'],

  data() {
    return {

    }
  },

  computed: {
    totalNights() {
      if (!this.startDate || !this.endDate) return null;

      const start = new Date(this.startDate);
      const end = new Date(this.endDate);
      const timeDiff = Math.abs(end.getTime() - start.getTime());
      return Math.ceil(timeDiff / (1000 * 3600 * 24));
    }
  },

  props: {
    accommodation: Object,
    startDate: String,
    endDate: String,
    totalPrice: Number,
  },
  methods: {
    confirmBooking() {
      // TODO Implement logic to confirm booking (e.g., send request to backend)

      if (this.accommodation) {
        console.log("Booking confirmed!");
        this.$emit('close-modal');

      } else {
        console.error("No Accommodation object available for booking");
      }

    },

    closeModal() {
      this.$emit('close-modal');
    },

  },

}
</script>

<style scoped>

.backdrop {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100;
}

.booking-confirm-modal {
  height: auto;
  width: auto;
  background: var(--white);
  padding: 2rem;
  border-radius: 10px;
  z-index: 999;
}

.modal-content {
  display: flex;
  flex-direction: column;
}

.title-container {
  position: relative;
  margin-bottom: 1rem;
}

.title {
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  color: var(--black);
}

.info-container {
  border-bottom: 1px solid #eee;
  padding: 1rem 0;
}

.inform-container {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 1rem 2rem;
  margin-bottom: 1rem;
  background: #e2eeef;
  border: 2px solid #5daab5;
  border-radius: 5px;
}

.material-symbols-outlined.book {
  color: #35818a;
  font-variation-settings:
      'FILL' 0,
      'wght' 500,
      'GRAD' 0,
      'opsz' 20
}

.inform-text {
  font-size: 14px;
  font-weight: 400;
  margin-left: 1rem;
  color: #35818a;
}

.section-title {
  font-size: 18px;
  font-weight: 500;
  color: #444;
  margin-bottom: 1rem;
}

.wrapper-trip {
  display: flex;
}

.text-trip-normal {
  font-size: 13px;
  font-weight: 400;
  color: var(--black);
  width: 100px;
}

.text-trip-bold {
  font-size: 13px;
  font-weight: 500;
  color: var(--black);
}

.wrapper {
  display: flex;
  justify-content: space-between;
}

.wrapper-total {
  display: flex;
  justify-content: space-between;
  margin-top: 3rem;
}

.text-normal {
  font-size: 16px;
  font-weight: 400;
  color: var(--black);
}

.text-bold {
  font-size: 16px;
  font-weight: 600;
  color: var(--black);
}

.buttons-container {
  display: flex;
  justify-content: space-between;
  gap: 2rem;
  margin-top: 2rem;
}

.confirm-button,
.cancel-button {
  font-size: 16px;
  width: 100%;
  padding: 1rem 5rem;
  cursor: pointer;
  border-radius: 10px;
}

.confirm-button {
  background: #5daab5;
  color: var(--white);
  font-weight: 600;
  border: none;
}

.cancel-button {
  background: none;
  color: var(--black);
  font-weight: 500;
  border: 1px solid #eee;
}

</style>