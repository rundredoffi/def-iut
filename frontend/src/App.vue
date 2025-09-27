<template>
	<NavbarComp v-if="!$route.meta.hiddenNav"/>
	<router-view/>
	<FooterComp/>
</template>

<script>
import '@/assets/styles/app.css'
import FooterComp from "@/components/FooterComp";
import NavbarComp from "@/components/NavbarComp";
export default {
	components: {
		FooterComp,
		NavbarComp
	}
}
</script>