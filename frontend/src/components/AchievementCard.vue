<template>
  <li class="achievement-item">
    <span
        class="badge"
        :style="{ backgroundColor: badge.color, color: '#FFFFFF' }"
        :title="badge.description"
    >
      {{ badge.title }}
    </span>
    <span>{{ formatDate(badge.date) }}</span>
  </li>
</template>

<script>
export default {
  name: "AchievementCard",
  props: {
    badge: {
      type: Object,
      required: true
    }
  },
  methods:{
    formatDate(dateString){
      return new Date(dateString).toLocaleDateString('fr-FR')
    }
  }
};
</script>

<style scoped>
.achievement-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 5px 0;
}

.badge {
  padding: 5px 10px;
  border-radius: 12px;
  font-weight: bold;
  min-width: 60px;
  text-align: center;
}
</style>
