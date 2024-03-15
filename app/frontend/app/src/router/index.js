import { createRouter, createWebHashHistory } from 'vue-router'
import HeroComponent from "@/components/hero/HeroComponent.vue";
import SignInComponent from "@/components/SignInComponent.vue";
import SignUpComponent from "@/components/SignUpComponent.vue";
import AccommodationsComponent from "@/components/AccommodationsComponent.vue";
import AccommodationDetailComponent from "@/components/AccommodationDetailComponent.vue";

const routes = [
    { path: '/', name: 'HOME', component: HeroComponent },
    { path: '/sign-in', name: 'SIGN-IN', component: SignInComponent, props: true },
    { path: '/sign-up', name: 'SIGN-UP', component: SignUpComponent, props: true },
    { path: '/accommodations', name: 'ACCOMMODATIONS', component: AccommodationsComponent },
    { path: '/accommodations/:id', name: 'ACCOMMODATION-DETAIL', component: AccommodationDetailComponent },
    { path: '/unauthorised', redirect: '/' }  // TODO make a page that informs about lack of authorisation
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router