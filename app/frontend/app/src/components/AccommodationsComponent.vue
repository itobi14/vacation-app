<template>

  <div class="content">

    <div class="side-bar">

    </div>

    <div class="container">

      <div class="accommodation-filter-bar">

        <div class="accommodation-filter-container">

          <div class="accommodation-wrapper"
               @click="displayAccommodations('hotel')"
               :class="{ isSelected: selectedAccommodationType === 'hotel' }">
            <img src="../assets/icons/building.png" class="accommodation-icon" alt="Hotel">
            <span> {{ hotel }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('cabin')"
               :class="{ isSelected: selectedAccommodationType === 'cabin' }">
            <img src="../assets/icons/cabin.png" class="accommodation-icon" alt="Cabin">
            <span> {{ cabin }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('bungalow')"
               :class="{ isSelected: selectedAccommodationType === 'bungalow' }">
            <img src="../assets/icons/bungalow.png" class="accommodation-icon" alt="Bungalow">
            <span> {{ bungalow }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('cottage')"
               :class="{ isSelected: selectedAccommodationType === 'cottage' }">
            <img src="../assets/icons/cottage.png" class="accommodation-icon" alt="Cottage">
            <span> {{ cottage }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('resort')"
               :class="{ isSelected: selectedAccommodationType === 'resort' }">
            <img src="../assets/icons/resort.png" class="accommodation-icon" alt="Resort">
            <span> {{ bungalow }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('camping')"
               :class="{ isSelected: selectedAccommodationType === 'camping' }">
            <img src="../assets/icons/camping.png" class="accommodation-icon" alt="Camping">
            <span> {{ camping }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('cruise')"
               :class="{ isSelected: selectedAccommodationType === 'cruise' }">
            <img src="../assets/icons/cruise.png" class="accommodation-icon" alt="Cruise">
            <span> {{ cruise }} </span>
          </div>

          <div class="accommodation-wrapper"
               @click="displayAccommodations('all')"
               :class="{ isSelected: selectedAccommodationType === 'all' }">
            <img src="../assets/icons/landmark.png" class="accommodation-icon" alt="All">
            <span> {{ all }} </span>
          </div>

        </div>

      </div>

      <div class="search-container">

        <div class="searchbarWrapper">
          <span class="material-symbols-outlined">search</span>
          <input v-model="searchQuery" type="search" placeholder="Search..." class="searchbar">
        </div>

        <div class="grid-filter-options">
          <div class="grid-large"
               @click="switchToGrid('large')"
               :class="{ isGridSelected: selectedGrid === 'large' }">
            <img src="../assets/grid-large.svg" class="grid-large-icon" alt="Grid Large Icon">
          </div>
          <div class="grid-small"
               @click="switchToGrid('small')"
               :class="{ isGridSelected: selectedGrid === 'small' }">
            <img src="../assets/grid-small.svg" class="grid-small-icon" alt="Grid Small Icon">
          </div>
          <div class="grid-rows"
               @click="switchToGrid('rows')"
               :class="{ isGridSelected: selectedGrid === 'rows' }">
            <img src="../../src/assets/rows.svg" class="rows-icon" alt="Rows Icon">
          </div>
        </div>

      </div>

      <div class="accommodations">
        <div class="accommodations-wrapper" :class="accommodationWrapper">
          <div v-for="accommodation in filteredAccommodations"
               :key="accommodation.id"
               :class="accommodationItem"
               @click="goToDetailView(accommodation.id)">
            <img :src="accommodation.imgUrl" class="accommodation-img" alt="Accommodation Image">
            <h2 :class="accommodationName">{{ accommodation.name }}</h2>
            <div class="info-wrapper">
              <div class="country-city-wrapper">
                <p :class="accommodationCountryCity">{{ accommodation.country }},&nbsp;&nbsp;</p>
                <p :class="accommodationCountryCity">{{ accommodation.city }}</p>
              </div>
              <p :class="accommodationPrice">${{ accommodation.price }}</p>
            </div>
<!--            <div v-if="isAuthenticated" class="favorite" @click="toggleFavorite">-->
<!--              <span class="material-symbols-outlined favorite" v-if="isFavorite" :class="{ filled: isFavorite }">star</span>-->
<!--            </div>-->
          </div>
        </div>
      </div>

    </div>

  </div>

</template>

<script>

export default {
  name: "AccommodationsComponent",
  inject: ['accommodationsService', 'sessionService'],

  data() {
    return {
      hotel: "Hotel",
      cabin: "Cabin",
      bungalow: "Bungalow",
      cottage: "Cottage",
      resort: "Resort",
      camping: "Camping",
      cruise: "Cruise",
      all: "All",

      allAccommodations: [],
      selectedAccommodations: [],
      selectedAccommodationType: "hotel",
      selectedAccommodation: null,

      hotels: [],
      cabins: [],
      bungalows: [],
      apartments: [],
      cottages: [],
      resorts: [],
      campings: [],
      cruises: [],

      searchQuery: '',
      selectedGrid: "large",
      gridLayout: 'large',
    }
  },

  async created() {
    this.allAccommodations = await this.accommodationsService.findAll();
    console.log("Fetched accommodations: ", this.allAccommodations);

    // Sort accommodations into arrays based on type
    this.allAccommodations.forEach(accommodation => {
      const type = accommodation.type.toLowerCase();
      switch (type) {
        case 'hotel':
          this.hotels.push(accommodation);
          break;
        case 'cabin':
          this.cabins.push(accommodation);
          break;
        case 'bungalow':
          this.bungalows.push(accommodation);
          break;
        case 'cottage':
          this.cottages.push(accommodation);
          break;
        case 'resort':
          this.resorts.push(accommodation);
          break;
        case 'camping':
          this.campings.push(accommodation);
          break;
        case 'cruise':
          this.cruises.push(accommodation);
          break;
      }
    });

    this.selectedAccommodations = this.hotels;

  },

  computed: {
    filteredAccommodations() {
      const accommodations = Object.values(this.selectedAccommodations);
      const searchQuery = this.searchQuery.toLowerCase();
      return accommodations.filter(accommodation => {
        const nameMatch = accommodation.name.toLowerCase().includes(searchQuery);
        const countryMatch = accommodation.country.toLowerCase().includes(searchQuery);
        const cityMatch = accommodation.city.toLowerCase().includes(searchQuery);
        return nameMatch || countryMatch || cityMatch;
      });
    },
    accommodationWrapper() {
      return `accommodations-wrapper-${this.gridLayout}`;
    },
    accommodationItem() {
      return `accommodation-${this.gridLayout}`;
    },
    accommodationName() {
      return `accommodation-name-${this.gridLayout}`;
    },
    accommodationCountryCity() {
      return `accommodation-country-city-${this.gridLayout}`;
    },
    accommodationPrice() {
      return `accommodation-price-${this.gridLayout}`;
    },

    isAuthenticated() {
      // console.log("isAuthenticated=",this.sessionService.isAuthenticated());
      return this.sessionService.isAuthenticated();
    },
  },

  methods: {

    displayAccommodations(type) {
      this.selectedAccommodationType = type;
      switch (type) {
        case 'hotel':
          this.selectedAccommodations = this.hotels;
          break;
        case 'cabin':
          this.selectedAccommodations = this.cabins;
          break;
        case 'bungalow':
          this.selectedAccommodations = this.bungalows;
          break;
        case 'cottage':
          this.selectedAccommodations = this.cottages;
          break;
        case 'resort':
          this.selectedAccommodations = this.resorts;
          break;
        case 'camping':
          this.selectedAccommodations = this.campings;
          break;
        case 'cruise':
          this.selectedAccommodations = this.cruises;
          break;
        case 'all':
          this.selectedAccommodations = this.allAccommodations;
          break;
      }
    },

    switchToGrid(layout) {
      this.gridLayout = layout;
      this.selectedGrid = layout;
    },

    toggleFavorite() {
      this.isFavorite = !this.isFavorite;
      if (this.isFavorite) {
        // Add the accommodation to favorites list (e.g., in localStorage or database)
      } else {
        // Remove the accommodation from favorites list (e.g., in localStorage or database)
      }
    },

    goToDetailView(id) {
      this.$router.push('/accommodations/' + id)
    },

  },

}
</script>

<style scoped>

.content {
  display: flex;
  //background: #f5f5f5;
  border-top: 2px solid var(--black);
  width: 85svw;
  height: auto;
}

.side-bar {
  height: auto;
  width: 15svw;
  background: #f5f5f5;
}

.container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
}

.accommodation-filter-bar {
  display: flex;
  align-items: center;
  height: auto;
  width: 100%;
  padding: 1rem;
  border-bottom: 2px solid var(--black);
  //background: lightcoral;
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
  opacity: 0.6;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation-wrapper:not(.isSelected):hover {
  transform: translateY(-2px);
  opacity: 1;
}

.accommodation-icon {
  height: 50px;
  width: 50px;
}

span {
  font-size: 14px;
  font-weight: 500;
  color: var(--black)
}

.isSelected {
  opacity: 1;
}

.isSelected:before {
  content: '';
  position: absolute;
  bottom: 0;
  right: 0;
  height: 7px;
  width: 7px;
  background: #5daab5;
}

/* ------------------------------------------------------------------------------------------------------------------ */

.search-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 2rem;
  //background: lightsalmon;
}

.searchbarWrapper {
  position: relative;
  display: flex;
  align-items: center;
  gap: 20px;
  height: 50px;
  width: 500px;
  background: var(--white);
  padding: 0 15px;
  border-radius: 500px;
  border: 2px solid #eee;
}

.searchbar {
  width: 100%;
  border: none;
  outline: none;
}

input[type="search"]::-webkit-search-cancel-button {
  -webkit-appearance: none;
}

.searchbar:focus .searchbarWrapper {
  outline: 2px solid var(--black);
}

.material-symbols-outlined {
  color: #aaa;
  transform: scale(1.2);
  font-variation-settings:
      'FILL' 0,
      'wght' 600,
      'GRAD' 0,
      'opsz' 0
}

.grid-filter-options {
  display: flex;
  gap: 1rem;
}

.grid-large,
.grid-small,
.grid-rows {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  width: 40px;
  padding: 10px;
  border-radius: 8px;
  opacity: 0.6;
  cursor: pointer;
}

.grid-large:not(.isGridSelected):hover,
.grid-small:not(.isGridSelected):hover,
.grid-rows:not(.isGridSelected):hover {
  background: #f5f5f5;
  opacity: 1;
}

.isGridSelected {
  background: #f5f5f5;
  opacity: 1;
}

.grid-large-icon,
.grid-small-icon,
.rows-icon {
  height: 100%;
  width: 100%;
}

/* ------------------------------------------------------------------------------------------------------------------ */

.accommodations {
  height: auto;
  width: 100%;
  padding: 2rem;
}

.accommodations-wrapper-large {
  display: grid;
  column-gap: 2rem;
  row-gap: 2rem;
  grid-template-columns: repeat(3, 1fr);
}

.accommodation-large {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 2rem;
  border-radius: 10px;
  background: var(--white);
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodations-wrapper-small {
  display: grid;
  column-gap: 1.2rem;
  row-gap: 1.2rem;
  grid-template-columns: repeat(5, 1fr);
}

.accommodation-small {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 1.2rem;
  border-radius: 10px;
  background: var(--white);
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodations-wrapper-rows {
  display: grid;
  column-gap: 2rem;
  row-gap: 2rem;
  grid-template-columns: repeat(2, 1fr);
}

.accommodation-rows {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 2rem;
  border-radius: 10px;
  background: var(--white);
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation-large:hover,
.accommodation-small:hover,
.accommodation-rows:hover {
  transform: translateY(-5px);
}

.accommodation-img {
  height: 100px;
  width: 100%;
}

.accommodation-name-large {
  font-size: 18px;
  font-weight: 700;
  color: var(--black)
}

.accommodation-name-small {
  font-size: 14px;
  font-weight: 600;
  color: var(--black)
}

.country-city-wrapper {
  display: flex;
}

.accommodation-country-city-large {
  font-size: 14px;
  font-weight: 400;
  color: #555;
}

.accommodation-country-city-small {
  font-size: 12px;
  font-weight: 400;
  color: #555;
}

.accommodation-country-city-rows {
  font-size: 16px;
  font-weight: 400;
  color: #555;
}

.info-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.accommodation-price-large {
  font-size: 16px;
  font-weight: 600;
  color: var(--black);
}

.accommodation-price-small {
  font-size: 12px;
  font-weight: 600;
  color: var(--black);
}

.accommodation-price-rows {
  font-size: 20px;
  font-weight: 500;
  color: var(--black);
}

.favorite {
  position: absolute;
  top: 7%;
  right: 4%;
}

.material-symbols-outlined.favorite {
  color: var(--black);
  transform: scale(2);
  font-variation-settings:
      'FILL' 0,
      'wght' 400,
      'GRAD' 0,
      'opsz' 0
}

.material-symbols-outlined.favorite.filled {
  color: var(--black);
  transform: scale(2);
  font-variation-settings:
      'FILL' 1,
      'wght' 400,
      'GRAD' 0,
      'opsz' 0
}

</style>