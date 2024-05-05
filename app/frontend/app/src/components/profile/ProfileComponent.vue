<template>

  <div class="content">

    <div class="container">
      <div class="profile-container">
        <div class="info-container">
          <div class="profile-img-container">
            <img :src="getImgUrl(profileImg)" class="profile-img" alt="Profile Image">
          </div>
          <div class="user-name">{{ userName }}</div>
        </div>
      </div>

      <div class="favorites-container">
        <h2 class="section-title">Favorites</h2>
        <div class="favorites-wrapper">
          <div v-for="accommodation in this.favorites"
               :key="accommodation.id"
               class="accommodation"
               @click="goToDetailView(accommodation.id)">
            <img :src="getImgUrl(accommodation.imgUrl)"
                 class="accommodation-img"
                 alt="Accommodation Image">
            <div class="wrapper">
              <div class="header">
                <h3 class="accommodation-name">{{ accommodation.name }}</h3>
                <p class="accommodation-price">&euro; {{ accommodation.price }}</p>
              </div>
              <p class="country-city">{{ accommodation.country }}, &nbsp; {{ accommodation.city}} </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "ProfileComponent",
  inject: ['sessionService', 'accountsService'],

  data() {
    return {
      id: this.sessionService.currentAccount.id,
      userName: null,
      profileImg: null,
      favorites: [],
      bookingHistory: [],
    }
  },

  async created() {
    await this.fetchAccount();
    await this.fetchFavorites();
  },

  methods: {

    async fetchAccount() {
      const account = await this.accountsService.findById(this.id);
      console.log("fetched account: ", account)
      if (account) {
        this.userName = account.fullName;
        this.profileImg = account.profileImg;
      }
    },

    async fetchFavorites() {
      const favorites = await this.accountsService.findFavorites(this.id);
      if (favorites) {
        this.favorites = favorites
      }
    },

    getImgUrl(url) {
      if (url) {
        return (`static/${url}`);
      } else {
        return ('static/profile-pic-placeholder.jpeg');
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
  height: 100svh;
  width: 60svw;
  padding: 1rem;
}

.container {
  display: flex;
}

.profile-container {
  height: auto;
  width: 70%;
}

.profile-img-container {
  height: 150px;
  width: 150px;
}

.info-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 30%;
  border-bottom: 2px solid var(--color-primary-lighter);
}

.profile-img {
  height: 100%;
  width: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.user-name {
  margin: 1rem 0;
  font-size: 20px;
  font-weight: 600;
  color: var(--color-black);
}

/* FAVORITES ----------------------------------------------------------------------------------------------------- */

.favorites-container {
  padding: 1rem;
  width: 300px;
  border-radius: 10px;
  background-color: var(--color-primary-lighter);
}

.section-title {
  text-align: center;
  font-size: 1.2em;
  font-weight: 600;
  color: var(--color-black);
  margin-bottom: 1rem;
}

.favorites-wrapper {
  display: flex;
  flex-direction: column;
  min-height: 350px;
  max-height: 500px;
  padding: 0 1rem 0 0;
  overflow-y: auto;
}

.favorites-wrapper::-webkit-scrollbar {
  width: 10px;
}

.favorites-wrapper::-webkit-scrollbar-thumb {
  background-color: var(--color-darkgrey);
}

.favorites-wrapper::-webkit-scrollbar-track {
  background-color: var(--color-lightgrey);
}

.accommodation {
  display: flex;
  flex-direction: column;
  width: 100%;
  background: none;
  padding: 1rem;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation:hover {
  background-color: var(--color-lightgrey);
  border-radius: 10px;
}

.accommodation-img {
  height: 120px;
  width: 100%;
  border-radius: 10px;
  object-fit: cover;
}

.wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 1rem;
  height: auto;
  width: 100%;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.accommodation-name {
  font-size: 1em;
  font-weight: 600;
  color: var(--color-black)
}

.country-city {
  font-size: .8em;
  font-weight: 400;
  color: var(--color-black);
}

.accommodation-price {
  font-size: .8em;
  font-weight: 500;
  color: var(--color-black);
}

</style>