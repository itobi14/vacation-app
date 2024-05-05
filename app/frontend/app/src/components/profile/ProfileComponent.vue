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
    // await this.fetchBookingHistory();
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

    // async fetchBookingHistory() {
    //   const bookingHistory = await this.accountsService.findBookingHistory(this.id);
    //   if (bookingHistory) {
    //     this.bookingHistory = bookingHistory
    //   }
    // },

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

  computed: {

  },

}
</script>

<style scoped>

.content {
  height: 100svh;
  width: 60svw;
  padding: 1rem;
  //background: lightblue;
}

.container {
  display: flex;
}

.profile-container {
  height: auto;
  width: 70%;
}

.profile-img-container {
  height: 200px;
  width: 200px;
}

.info-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 30%;
  border-bottom: 2px solid var(--divider);
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
  color: var(--black);
}

/* FAVORITES ----------------------------------------------------------------------------------------------------- */

.favorites-container {
  padding: 1rem;
  width: 30%;
  //background-color: #35818a;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--black);
  padding-bottom: 1rem;
}

.favorites-wrapper {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  height: 80svh;
  padding: 0 1rem 0 0;
  overflow-y: auto;
}

.favorites-wrapper::-webkit-scrollbar {
  width: 10px;
}

.favorites-wrapper::-webkit-scrollbar-thumb {
  background-color: #D5D5D5;
}

.favorites-wrapper::-webkit-scrollbar-track {
  background-color: var(--divider);
}

.accommodation {
  display: flex;
  flex-direction: column;
  width: 100%;
  background: none;
  border-top: 2px solid #f5f5f5;
  padding: 1rem;
  transition: 0.2s ease-in-out;
  cursor: pointer;
}

.accommodation:hover {
  background-color: #F1F5F5;
  border-top: 2px solid var(--white);
  border-radius: 10px;
}

.accommodation-img {
  height: 200px;
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

.country-city {
  font-size: 14px;
  font-weight: 400;
  color: var(--black);
}

.accommodation-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--black)
}

.accommodation-price {
  font-size: 12px;
  font-weight: 500;
  color: var(--black);
}

</style>