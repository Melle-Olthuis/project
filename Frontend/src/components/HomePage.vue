<template>
  <div class="home-page">
    <h1>Mijn Notities</h1>

    <div class="controls">
      <button @click="addNote">+ Nieuwe notitie</button>
      <input type="text" v-model="searchTerm" placeholder="Zoek op titel..." />
      <select v-model="selectedTag">
        <option value="">Alle tags</option>
        <option v-for="tag in allTags" :key="tag" :value="tag">{{ tag }}</option>
      </select>
      <button @click="exportNotes">Exporteren</button>
    </div>

    <div class="notes-list">
      <div
        v-for="note in filteredNotes"
        :key="note.id"
        class="note-card"
        :class="{ done: note.completed, urgent: note.urgent }"
      >
        <div class="note-header">
          <button
            class="complete-btn"
            @click="note.completed = !note.completed"
            title="Markeer als voltooid"
          >
            ✅
          </button>
          <button class="urgent-btn" @click="note.urgent = !note.urgent" title="Markeer als haast">
            ⏰
          </button>
          <button class="archive-btn" @click="archiveNote(note)" title="Archiveer">📦</button>
          <button class="delete-btn" @click="deleteNote(note.id)" title="Verwijder notitie">
            ✖
          </button>
        </div>

        <input v-model="note.title" placeholder="Titel..." class="note-title" />
        <textarea
          v-model="note.content"
          placeholder="Schrijf je notitie hier..."
          class="note-content"
        ></textarea>

        <div class="dates">
          <div>
            <label>Vandaag:</label>
            <input type="date" v-model="note.date" disabled />
          </div>
          <div>
            <label>Einddatum:</label>
            <input type="date" v-model="note.deadline" />
          </div>
        </div>

        <input
          v-model="note.tagsInput"
          @blur="updateTags(note)"
          @keydown.enter.prevent="updateTags(note)"
          placeholder="Tags (comma separated)"
          class="tags-input"
        />
        <small>Tags: {{ note.tags.join(', ') }}</small>
        <div class="tag-icons">
          <span v-for="tag in note.tags" :key="tag">{{ iconForTag(tag) }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import axios from 'axios';
import { useUserStore } from '../stores/user';

const tagIcons = {
  school: '📚',
  shopping: '🛒',
  work: '💼',
  personal: '🙋',
};

function iconForTag(tag) {
  return tagIcons[tag.toLowerCase()] || '🔖';
}

let noteId = 1;
const notes = ref([]);
const userStore = useUserStore();
const searchTerm = ref('');
const selectedTag = ref('');

const allTags = computed(() => {
  const set = new Set();
  notes.value.forEach((n) => n.tags?.forEach((t) => set.add(t)));
  return Array.from(set);
});

const filteredNotes = computed(() => {
  return notes.value.filter((note) => {
    const matchesSearch =
      note.title.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
      (note.tags &&
        note.tags.some((tag) => tag.toLowerCase().includes(searchTerm.value.toLowerCase())));
    const matchesTag = !selectedTag.value || note.tags?.includes(selectedTag.value);
    return !note.archived && matchesSearch && matchesTag;
  });
});

function addNote() {
  const today = new Date().toISOString().split('T')[0];
  const newNote = {
    id: noteId++,
    title: '',
    content: '',
    date: today,
    deadline: '',
    completed: false,
    urgent: false,
    archived: false,
    tags: [],
    tagsInput: '',
  };
  notes.value.unshift(newNote);
  axios.post('http://localhost:8080/api/notes', newNote).catch((err) => console.error(err));
}

function deleteNote(id) {
  notes.value = notes.value.filter((note) => note.id !== id);
  axios.delete(`http://localhost:8080/api/notes/${id}`).catch((err) => console.error(err));
}

function archiveNote(note) {
  note.archived = true;
}

function exportNotes() {
  window.print();
}

function updateTags(note) {
  note.tags = note.tagsInput
    .split(',')
    .map((t) => t.trim())
    .filter(Boolean);
}

onMounted(() => {
  const saved = localStorage.getItem(`notes_${userStore.username}`)
  if (saved) {
    notes.value = JSON.parse(saved)
    noteId = Math.max(...notes.value.map((n) => n.id), 0) + 1
  } else {
    axios
      .get('http://localhost:8080/api/notes')
      .then((response) => {
        notes.value = response.data.map((n) => ({
          ...n,
          tagsInput: n.tags ? n.tags.join(', ') : '',
        }))
        noteId = Math.max(...notes.value.map((n) => n.id), 0) + 1
      })
      .catch((err) => console.error('Kan notities niet ophalen:', err))
  }
})

watch(notes, (val) => {
  if (userStore.username) {
    localStorage.setItem(`notes_${userStore.username}`, JSON.stringify(val))
  }
}, { deep: true })
</script>

<style scoped>
.home-page {
  max-width: 800px;
  margin: 2rem auto;
  padding: 1rem;
  font-family: 'Segoe UI', sans-serif;
  background-color: white;
  color: black;
}

.controls {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.controls button {
  padding: 0.5rem 1rem;
  background-color: var(--color-primary, #007bff);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.controls input {
  flex-grow: 1;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.controls select {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.notes-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.note-card {
  background: #fdfdfd;
  padding: 1.5rem;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
  position: relative;
  text-align: center;
}

.note-card.done {
  background-color: #d6ffe0;
  border-left: 4px solid green;
}

.note-card.urgent {
  background-color: #fff0cc;
  border-left: 4px solid orange;
}

.note-header {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.note-title {
  font-weight: bold;
  font-size: 1.3rem;
  border: none;
  background: transparent;
  width: 100%;
  text-align: center;
  margin-bottom: 0.75rem;
}

.note-content {
  width: 100%;
  height: 100px;
  resize: vertical;
  border: none;
  background: transparent;
  text-align: center;
  padding: 0.5rem;
  font-size: 1rem;
  margin-bottom: 1rem;
}

.note-title:focus,
.note-content:focus {
  outline: 1px solid #ccc;
  background: white;
}

.complete-btn,
.urgent-btn,
.archive-btn,
.delete-btn {
  background: transparent;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.complete-btn {
  color: green;
}

.urgent-btn {
  color: orange;
}

.delete-btn {
  color: red;
}

.archive-btn {
  color: blue;
}

.complete-btn:hover,
.urgent-btn:hover,
.archive-btn:hover,
.delete-btn:hover {
  transform: scale(1.2);
}

.dates {
  display: flex;
  justify-content: center;
  gap: 2rem;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.dates label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.2rem;
}

.dates input[type='date'] {
  padding: 0.3rem 0.6rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: center;
}

.tags-input {
  width: 100%;
  padding: 0.3rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 0.5rem;
}

.tag-icons span {
  margin-right: 0.25rem;
}
</style>
