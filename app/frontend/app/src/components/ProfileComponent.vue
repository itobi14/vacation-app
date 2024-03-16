<template>

  <div class="content">

    <div class="profile-container">
      <img :src="getImgUrl(profileImg)" class="profile-img" alt="Profile Image">
    </div>

    <div class="favorites-history-container">
      <h2 class="section-title">Favorites</h2>
      <div v-for="accommodation in this.favorites"
           :key="accommodation.id"
           class="item">
        <img :src="getImgUrl(accommodation.imgUrl)"
             class="accommodation-img"
             alt="Accommodation Image">
        <h3>{{ accommodation.name }}</h3>
        <p>{{ accommodation.description }}</p>
      </div>
    </div>

<!--    <div class="booking-history-container">-->
<!--      <h2 class="section-title">Booking history</h2>-->
<!--      <div v-for="accommodation in this.bookingHistory"-->
<!--           :key="accommodation.id"-->
<!--           class="item">-->
<!--        <img :src="getImgUrl(accommodation.imgUrl)"-->
<!--             class="accommodation-img"-->
<!--             alt="Accommodation Image">-->
<!--        <h3>{{ accommodation.name }}</h3>-->
<!--        <p>{{ accommodation.description }}</p>-->
<!--      </div>-->
<!--    </div>-->

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

  },

  computed: {

  },

}
</script>

<style scoped>

.content {
  height: 100svh;
  width: 85svw;
  background: #f5f5f5;
}

.profile-container {
  height: 200px;
  width: 200px;
}

.profile-img {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.accommodation-img {
  height: 100px;
}

</style>