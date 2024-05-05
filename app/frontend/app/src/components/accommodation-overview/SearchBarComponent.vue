<template>
  <div class="search-container">

    <div class="searchbarWrapper">
      <span class="material-symbols-outlined">search</span>
<!--      <input v-model="searchQuery" type="search" placeholder="Search..." class="searchbar">-->
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
</template>

<script>
export default {
  name: "SearchBarComponent",
  props: {
    searchQuery: String,
    selectedGrid: String
  },
  emits: ['switch-grid'],

  methods: {
    switchToGrid(gridType) {
      this.$emit('switch-grid', gridType);
    },
  }
}
</script>

<style scoped>

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

</style>