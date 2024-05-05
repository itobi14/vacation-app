<template>
  <div class="accommodations-overview-component">
    <div class="container">
      <accommodations-filter-component
          :selectedAcccomodationType="selectedAccommodationType"
          @filter-accommodations="filterAccommodations"
      ></accommodations-filter-component>
      <search-bar-component
          :searchQuery="searchQuery"
          :selectedGrid="selectedGrid"
          @switch-grid="switchToGrid"
      ></search-bar-component>
      <accommodations-overview-component
          :selectedAccommodations="selectedAccommodations"
          :accommodations="searchAccommodations"
          :selectedAccommodation="selectedAccommodation"
          @go-to-detail-view="goToDetailView"
      ></accommodations-overview-component>
    </div>
  </div>
</template>

<script>

import SearchBarComponent from "@/components/accommodation-overview/SearchBarComponent.vue";
import AccommodationsFilterComponent from "@/components/accommodation-overview/AccommodationsFilterComponent.vue";
import AccommodationsOverviewComponent from "@/components/accommodation-overview/AccommodationsOverviewComponent.vue";

export default {
  name: "AccommodationsPageComponent",
  components: {
    'accommodations-filter-component': AccommodationsFilterComponent,
    'search-bar-component': SearchBarComponent,
    'accommodations-overview-component': AccommodationsOverviewComponent
  },

  inject: ['accountsService', 'accommodationsService', 'sessionService'],

  data() {
    return {
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
    }
  },

  async created() {
    await this.fetchAccommodations()
  },

  computed: {
    searchAccommodations() {
      const accommodations = Object.values(this.selectedAccommodations);
      const searchQuery = this.searchQuery.toLowerCase();

      return accommodations.filter(accommodation => {
        const nameMatch = accommodation.name.toLowerCase().includes(searchQuery);
        const countryMatch = accommodation.country.toLowerCase().includes(searchQuery);
        const cityMatch = accommodation.city.toLowerCase().includes(searchQuery);
        return nameMatch || countryMatch || cityMatch;
      });
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

    filterAccommodations(type) {
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

    switchToGrid(gridType) {
      this.selectedGrid = gridType;
    },

    goToDetailView(id) {
      this.$router.push('/accommodations/' + id)
    },

  },
}
</script>

<style scoped>

.accommodations-overview-component {
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

</style>