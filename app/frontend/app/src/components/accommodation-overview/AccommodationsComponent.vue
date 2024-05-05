<template>

  <div class="content">

    <div class="container">

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

      <div class="search-container">

        <div class="searchbarWrapper">
          <span class="material-symbols-outlined">search</span>
          <input v-model="searchQuery" type="search" placeholder="Search..." class="searchbar">
        </div>

        <div class="grid-filter-options">
          <div class="grid-square"
               @click="switchToGrid('square')"
               :class="{ isGridSelected: this.selectedGrid === 'square' }">
            <img src="../../assets/grid-large.svg" class="grid-square-icon" alt="Grid Large Icon">
          </div>
          <div class="grid-rows"
               @click="switchToGrid('rows')"
               :class="{ isGridSelected: this.selectedGrid === 'rows' }">
            <img src="../../assets/rows.svg" class="grid-rows-icon" alt="Rows Icon">
          </div>
        </div>

      </div>

      <div class="accommodations">
        <div class="accommodations-wrapper" :class="accommodationWrapper">
          <div v-for="accommodation in filteredAccommodations"
               :key="accommodation.id"
               :class="accommodationItem"
               @click="goToDetailView(accommodation.id)">
            <img :src="getImgUrl(accommodation.imgUrl)" :class="accommodationImg" alt="Accommodation Image">
            <div :class="wrapper">
              <div class="header">
                <h2 class="accommodation-name">{{ accommodation.name }}</h2>
<!--                <div v-if="isAuthenticated" class="favorite-container">-->
<!--                  <div v-if="isFavorite">-->
<!--                    <span class="material-symbols-outlined favorite filled">favorite</span>-->
<!--                  </div>-->
<!--                  <div v-if="!isFavorite">-->
<!--                    <span class="material-symbols-outlined favorite">favorite</span>-->
<!--                  </div>-->
<!--                </div>-->
              </div>
              <div class="info-wrapper">
                <p class="accommodation-country-city">{{ accommodation.country }},&nbsp;&nbsp;</p>
                <p class="accommodation-country-city">{{ accommodation.city }}</p>
              </div>
              <div class="info-wrapper">
                <p class="accommodation-price-bold">&euro; {{ accommodation.price }}</p><p class="accommodation-price">&nbsp;per night</p>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>

</template>

<script>

export default {
  name: "AccommodationsComponent",
  inject: ['accountsService', 'accommodationsService', 'sessionService'],

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

      allAccommodations: [],
      selectedAccommodations: [],
      selectedAccommodationType: "all",
      selectedAccommodation: null,

      hotels: [],
      cabins: [],
      bungalows: [],
      apartments: [],
      chalets: [],
      resorts: [],
      campings: [],
      cruises: [],

      searchQuery: '',
      selectedGrid: "square",

      isFavorite: false,
    }
  },

  async created() {
    await this.fetchAccommodations()
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
      return `accommodations-wrapper-${this.selectedGrid}`;
    },
    accommodationItem() {
      return `accommodation-${this.selectedGrid}`;
    },
    wrapper() {
      return `wrapper-${this.selectedGrid}`;
    },
    accommodationImg() {
      return `accommodation-img-${this.selectedGrid}`;
    },
    accommodationDescription() {
      return `accommodation-description-${this.selectedGrid}`;
    },
    isAuthenticated() {
      // console.log("isAuthenticated=",this.sessionService.isAuthenticated());
      return this.sessionService.isAuthenticated();
    },
  },

  methods: {

    async fetchAccommodations() {
      this.allAccommodations = await this.accommodationsService.findAll();
      // console.log("Fetched accommodations: ", this.allAccommodations);

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
          case 'chalet':
            this.chalets.push(accommodation);
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

      this.selectedAccommodations = this.allAccommodations;

    },

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
        case 'chalet':
          this.selectedAccommodations = this.chalets;
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
      this.selectedGrid = layout;
    },

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/placeholder.svg');
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
  width: 80svw;
  height: auto;
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

/* SEARCHBAR ------------------------------------------------------------------------------------------------------------------ */

.search-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem 2rem;
}

.searchbarWrapper {
  position: relative;
  display: flex;
  align-items: center;
  gap: 1.2rem;
  height: 50px;
  width: 500px;
  background: var(--color-lightgrey);
  padding: 0 15px;
  border-radius: 500px;
}

.searchbar {
  width: 100%;
  border: none;
  outline: none;
  background: var(--color-lightgrey);
}

input[type="search"]::-webkit-search-cancel-button {
  -webkit-appearance: none;
}

input[type="search"] {
  color: var(--color-black);
  font-weight: 400;
}

.material-symbols-outlined {
  color: var(--color-darkgrey);
  margin-left: .5rem;
  transform: scale(1.5);
  font-variation-settings:
      'FILL' 0,
      'wght' 600,
      'GRAD' 0,
      'opsz' 0
}

/* GRID FILTER ------------------------------------------------------------------------------------------------------------------ */

.grid-filter-options {
  position: absolute;
  right: 0;
  display: flex;
  gap: 1rem;
}

.grid-square,
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

.grid-square:not(.isGridSelected):hover,
.grid-rows:not(.isGridSelected):hover {
  background: var(--color-lightgrey);
  opacity: 1;
}

.isGridSelected {
  background: var(--color-lightgrey);
  opacity: 1;
}

.grid-square-icon,
.grid-rows-icon {
  height: 100%;
  width: 100%;
}

/* ALL ------------------------------------------------------------------------------------------------------------------ */

.accommodations {
  height: auto;
  width: 100%;
  padding: 2rem 0;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.favorite-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 30px;
  width: 30px;
}

.accommodation-name {
  font-size: 1em;
  font-weight: 600;
  color: var(--color-black);
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.accommodation-country-city {
  font-size: .85em;
  font-weight: 400;
  color: var(--color-black);
}

.accommodation-price-bold {
  margin-top: 1rem;
  font-size: .85em;
  font-weight: 600;
  color: var(--color-black);
}

.accommodation-price {
  margin-top: 1rem;
  font-size: .85em;
  font-weight: 400;
  color: var(--color-black);
}

.info-wrapper {
  display: flex;
  align-items: center;
}

.material-symbols-outlined.favorite {
  color: var(--color-black);
  transform: scale(1.5);
  font-variation-settings:
      'FILL' 0,
      'wght' 400,
      'GRAD' 0,
      'opsz' 0
}

.material-symbols-outlined.favorite.filled {
  color: var(--color-black);
  transform: scale(1.5);
  font-variation-settings:
      'FILL' 1,
      'wght' 400,
      'GRAD' 0,
      'opsz' 0
}

/* SQUARE ------------------------------------------------------------------------------------------------------------------ */

.accommodations-wrapper-square {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  width: auto;
  column-gap: 1rem;
  row-gap: 1rem;
}

.accommodation-square {
  display: flex;
  flex-direction: column;
  height: auto;
  width: 250px;
  padding: 1rem;
  //transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation-square:hover {
  background-color: var(--color-lightgrey);
  border-radius: 8px;
}

.accommodation-img-square {
  height: 150px;
  width: 100%;
  border-radius: 8px;
  object-fit: cover;
}

.wrapper-square {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  height: auto;
  width: 100%;
  margin-top: 1rem;
}

/* ROWS ------------------------------------------------------------------------------------------------------------------ */

.accommodations-wrapper-rows {
  display: grid;
  column-gap: 2rem;
  row-gap: 2rem;
  grid-template-columns: repeat(2, 1fr);
}

.accommodation-rows {
  display: flex;
  width: 100%;
  background: none;
  padding: 1rem;
  //transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation-rows:hover {
  background-color: var(--color-lightgrey);
  border-radius: 8px;
}

.wrapper-rows {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 2rem;
  height: 100%;
  width: 65%;
}

.accommodation-img-rows {
  height: 175px;
  width: 35%;
  border-radius: 8px;
  object-fit: cover;
}

</style>