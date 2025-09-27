<template>
  <div v-html="content"></div>
</template>

<script>
import { marked } from 'marked';

export default {
  props: {
    challengeId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      content: ''
    };
  },
  watch: {
    challengeId: {
      handler: 'loadMarkdown',
      immediate: true
    }
  },
  methods: {

    /**
     * Load the markdown file and convert it to HTML
     */
    async loadMarkdown() {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`http://localhost:8081/api/challenges/${this.challengeId}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ token }),
        });
        if (!response.ok) {
          throw new Error('Failed to fetch challenge details');
        }
        const challenge = await response.json();

        const mdFilePath = `/challenges_doc/${challenge.name}.md`;
        const mdResponse = await fetch(mdFilePath);
        if (!mdResponse.ok) {
          throw new Error('Failed to fetch markdown file');
        }
        let text = await mdResponse.text();

        // Remove the 3 first lines (title already displayed)
        text = text.split('\n').slice(3).join('\n');

        this.content = marked(text);

        if (text === '') {
          this.content = '<p>Ceci est un challenge de test</p>';
        }

      } catch (error) {
        console.error('Error loading markdown file:', error);
      }
    }
  }
};
</script>

<style scoped>
div[v-html] {
  border: 1px solid red;
  padding: 10px;
}
</style>
