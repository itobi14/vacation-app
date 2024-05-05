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
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Name: </p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ accommodation.name }}</p>
            </div>
          </div>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">City: </p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ accommodation.city }}</p>
            </div>
          </div>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Country: </p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ accommodation.country }}</p>
            </div>
          </div>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Street: </p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ accommodation.streetNameNr }}</p>
            </div>
          </div>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Zip code: </p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ accommodation.zipCode }}</p>
            </div>
          </div>
        </div>

        <div class="info-container">
          <p class="section-title">Dates</p>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Check-in</p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ startDate }}</p>
            </div>
          </div>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Checkout</p>
            </div>
            <div class="wrapper">
              <p class="text-bold">{{ endDate }}</p>
            </div>
          </div>
        </div>

        <div class="info-container">
          <p class="section-title">Price details</p>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Price per night</p>
            </div>
            <div class="wrapper">
              <p class="text-bold">€{{ accommodation.price }}</p>
            </div>
          </div>
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-normal">Number of nights</p>
            </div>
            <div class="wrapper">
              <p class="text-bold">x{{ totalNights }}</p>
            </div>
          </div>
        </div>
        <div class="info-container">
          <div class="wrapper-data">
            <div class="wrapper">
              <p class="text-total">Total</p>
            </div>
            <div class="wrapper">
              <p class="text-total">€{{ totalPrice }}</p>
            </div>
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
  background: var(--color-white);
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
  font-size: 1.4em;
  font-weight: 600;
  text-align: center;
  color: var(--color-black);
}

.inform-container {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 1rem 2rem;
  margin-bottom: 1rem;
  background: var(--color-primary-lighter);
  border: 2px solid var(--color-primary-darker);
  border-radius: 5px;
}

.inform-text {
  font-size: .8em;
  font-weight: 400;
  margin-left: 2rem;
  color: var(--color-primary-darker);
}

.material-symbols-outlined.book {
  color: var(--color-primary-darker);
  font-variation-settings:
      'FILL' 0,
      'wght' 500,
      'GRAD' 0,
      'opsz' 20
}

.info-container {
  border-bottom: 1px solid var(--color-primary-lighter);
  padding: 1rem 0;
}

.section-title {
  font-size: .8em;
  font-weight: 600;
  color: var(--color-black);
  margin-bottom: 1rem;
}

.wrapper-data {
  display: flex;
}

.wrapper {
  width: 50%;
}

.text-normal {
  font-size: .85em;
  font-weight: 400;
  color: var(--color-black);
}

.text-bold {
  font-size: .85em;
  font-weight: 500;
  color: var(--color-black);
}

.text-total {
  font-size: .85em;
  font-weight: 600;
  color: var(--color-black);
}

.buttons-container {
  display: flex;
  justify-content: space-between;
  gap: 2rem;
  margin-top: 2rem;
}

.confirm-button,
.cancel-button {
  font-size: 1em;
  width: 50%;
  min-width: 220px;
  padding: 1rem 0;
  cursor: pointer;
  border-radius: 5px;
}

.confirm-button {
  background: var(--color-primary);
  color: var(--color-white);
  font-weight: 600;
  border: none;
  transition: .2s ease-in-out;
  opacity: .85;
}

.cancel-button {
  background: none;
  color: var(--color-black);
  font-weight: 500;
  border: 2px solid #E2EEEF;
}

.confirm-button:hover {
  opacity: 1;
}

</style>