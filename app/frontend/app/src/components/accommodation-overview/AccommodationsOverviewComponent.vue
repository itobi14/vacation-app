<template>
  <div class="accommodations">
    <div class="accommodations-wrapper" :class="accommodationWrapper">
      <div v-for="accommodation in accommodations"
           :key="accommodation.id"
           :class="accommodationItem"
           @click="goToDetailView(accommodation.id)">
        <img :src="getImgUrl(accommodation.imgUrl)" :class="accommodationImg" alt="Accommodation Image">
        <div :class="wrapper">
          <div class="header">
            <h2 class="accommodation-name">{{ accommodation.name }}</h2>
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
</template>

<script>
export default {
  name: "AccommodationsOverviewComponent",
  props: {
    accommodations: Array
  },
  emits: ['go-to-detail-view'],

  methods: {
    goToDetailView(id) {
      this.$emit('go-to-detail-view', id);
    },

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/placeholder.svg');
      }
    },
  },

  computed: {
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
  }
}
</script>

<style scoped>

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