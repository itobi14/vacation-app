<template>
  <div class="accommodation-filter-bar">
    <div class="accommodation-filter-container">

      <div class="accommodation-wrapper"
           @click="displayAccommodations('all')"
           :class="{ isSelected: this.selectedAccommodationType === 'all' }">
        <img src="../../assets/icons/landmark.png" class="accommodation-icon" alt="All">
        <span> {{ all }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('hotel')"
           :class="{ isSelected: this.selectedAccommodationType === 'hotel' }">
        <img src="../../assets/icons/building.png" class="accommodation-icon" alt="Hotel">
        <span> {{ hotel }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('cabin')"
           :class="{ isSelected: this.selectedAccommodationType === 'cabin' }">
        <img src="../../assets/icons/cabin.png" class="accommodation-icon" alt="Cabin">
        <span> {{ cabin }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('bungalow')"
           :class="{ isSelected: this.selectedAccommodationType === 'bungalow' }">
        <img src="../../assets/icons/bungalow.png" class="accommodation-icon" alt="Bungalow">
        <span> {{ bungalow }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('chalet')"
           :class="{ isSelected: this.selectedAccommodationType === 'chalet' }">
        <img src="../../assets/icons/chalet.png" class="accommodation-icon" alt="Chalet">
        <span> {{ chalet }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('resort')"
           :class="{ isSelected: this.selectedAccommodationType === 'resort' }">
        <img src="../../assets/icons/resort.png" class="accommodation-icon" alt="Resort">
        <span> {{ resort }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('camping')"
           :class="{ isSelected: this.selectedAccommodationType === 'camping' }">
        <img src="../../assets/icons/camping.png" class="accommodation-icon" alt="Camping">
        <span> {{ camping }} </span>
      </div>

      <div class="accommodation-wrapper"
           @click="displayAccommodations('cruise')"
           :class="{ isSelected: this.selectedAccommodationType === 'cruise' }">
        <img src="../../assets/icons/cruise.png" class="accommodation-icon" alt="Cruise">
        <span> {{ cruise }} </span>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "AccommodationsFilterComponent",
  props: {
    selectedAccommodationType: String
  },
  emits: ['filter-accommodations'],

  data() {
    return {
      hotel: "Hotel",
      cabin: "Cabin",
      bungalow: "Bungalow",
      chalet: "Chalet",
      resort: "Resort",
      camping: "Camping",
      cruise: "Cruise",
      all: "All",
    }
  },

  methods: {
    displayAccommodations(accommodationType) {
      this.$emit('filter-accommodations', accommodationType);
    },
  }
}
</script>

<style scoped>

.accommodation-filter-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  height: auto;
  width: 100%;
  padding: 1rem;
}

.accommodation-filter-container {
  display: flex;
  align-items: center;
  justify-content: left;
  gap: 2rem;
}

.accommodation-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 1rem;
  width: 100px;
  padding: 0.5rem;
  opacity: 0.5;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation-icon {
  height: 40px;
  width: 40px;
}

span {
  font-size: .8em;
  font-weight: 600;
  color: var(--color-black)
}

.isSelected {
  opacity: 1;
}

.accommodation-wrapper:not(.isSelected):hover {
  transform: translateY(-2px);
  opacity: 1;
}

</style>