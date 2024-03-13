<template>

  <div class="footer">
    <div class="footer-container">
      <div class="footer-section">
        <h3 class="section-title">Explore</h3>
        <ul>
          <li><a href="#">About Us</a></li>
          <li><a href="#">How it Works</a></li>
          <li><a href="#">Partner with Us</a></li>
          <li><a href="#">Travel Blog</a></li>
        </ul>
      </div>
      <div class="footer-section">
        <h3 class="section-title">Support</h3>
        <ul>
          <li><a href="#">FAQs</a></li>
          <li><a href="#">Booking Guide</a></li>
          <li><a href="#">Payment Options</a></li>
          <li><a href="#">Contact Us</a></li>
        </ul>
      </div>
      <div class="footer-section">
        <h3 class="section-title">Popular Destinations</h3>
        <ul>
          <li><a href="#">France</a></li>
          <li><a href="#">Italy</a></li>
          <li><a href="#">Spain</a></li>
          <li><a href="#">Germany</a></li>
        </ul>
      </div>
      <div class="footer-section">
        <h3 class="section-title">Socials</h3>
        <ul>
          <li><a href="#">Instagram</a></li>
          <li><a href="#">Facebook</a></li>
          <li><a href="#">X</a></li>
          <li><a href="#">TikTok</a></li>
        </ul>
      </div>
    </div>
    <div class="copyright">
      <p>&copy; {{ year }} All rights reserved. Europe Getaway</p>
    </div>
  </div>

</template>

<script>
export default {
  name: "FooterComponent",

  data() {
    return {
      year: new Date().getFullYear()
    }
  },

}
</script>

<style scoped>

.footer {
  background: #f5f5f5;
  padding: 50px 250px 30px 250px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.footer-container {
  display: flex;
  justify-content: space-between;
  gap: 5rem;
  width: auto;
  padding: 0 0 0 125px;
  margin: 0 auto;
  color: var(--black);
}

.footer-section {
  flex: 1;
  width: 225px;
  margin-bottom: 2rem;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 2rem;
}

ul {
  display: flex;
  justify-content: center;
  flex-direction: column;
  gap: 10px;
  list-style: none;
}

li {
  color: #555;
  transition: 0.3s ease-in-out;
}

.footer-section a:hover {
  color: var(--black);
  transform: translateX(10px);
}

.footer-section a {
  color: inherit;
  text-decoration: none;
  padding-left: 2px;
}

.social-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.social-links li {
  margin-right: 15px;
}

.social-links i {
  font-size: 20px;
  color: #333;
}

.copyright {
  margin-top: 3rem;
  text-align: center;
  font-size: 14px;
  color: #777;
}

</style>