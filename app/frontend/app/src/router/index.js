import { createRouter, createWebHashHistory } from 'vue-router'
import HomeComponent from "@/components/Hero/HeroComponent.vue";
import SignInComponent from "@/components/SignInComponent.vue";
import AccommodationsComponent from "@/components/AccommodationsComponent.vue";

const routes = [
    { path: '/', name: 'HOME', component: HomeComponent },
    { path: '/sign-in', name: 'SIGN-IN', component: SignInComponent, props: true },
    { path: '/accommodations', name: 'ACCOMMODATIONS', component: AccommodationsComponent },
    { path: '/unauthorised', redirect: '/' }  // TODO make a page that informs about lack of authorisation
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router