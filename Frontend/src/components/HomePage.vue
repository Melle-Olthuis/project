<template>
  <div class="home-page">
    <div class="page-header">
      <h1 class="page-title">✨ Mijn Notities</h1>
      <p class="page-subtitle">Organiseer je gedachten met stijl</p>
    </div>

    <div class="controls">
      <button @click="addNote" class="primary-btn">
        <span class="btn-icon">➕</span>
        Nieuwe notitie
      </button>
      <div class="search-container">
        <input 
          type="text" 
          v-model="searchTerm" 
          placeholder="🔍 Zoek op titel..." 
          class="search-input"
        />
      </div>
      <select v-model="selectedTag" class="tag-select">
        <option value="">Alle tags</option>
        <option v-for="tag in allTags" :key="tag" :value="tag">{{ tag }}</option>
      </select>
      <button @click="exportNotes" class="secondary-btn">
        <span class="btn-icon">📄</span>
        Exporteren
      </button>
    </div>

    <div class="notes-grid">
      <div
        v-for="note in filteredNotes"
        :key="note.id"
        class="note-card"
        :class="{ 
          'note-done': note.completed, 
          'note-urgent': note.urgent,
          'note-has-deadline': note.deadline 
        }"
      >
        <div class="note-header">
          <div class="note-actions">
            <button
              class="action-btn complete-btn"
              @click="note.completed = !note.completed"
              :title="note.completed ? 'Markeer als niet voltooid' : 'Markeer als voltooid'"
              :class="{ active: note.completed }"
            >
              ✓
            </button>
            <button 
              class="action-btn urgent-btn" 
              @click="note.urgent = !note.urgent" 
              title="Markeer als urgent"
              :class="{ active: note.urgent }"
            >
              ⚠
            </button>
            <button 
              class="action-btn archive-btn" 
              @click="archiveNote(note)" 
              title="Archiveer"
            >
              📁
            </button>
            <button 
              class="action-btn delete-btn" 
              @click="deleteNote(note.id)" 
              title="Verwijder notitie"
            >
              🗑
            </button>
          </div>
        </div>

        <input 
          v-model="note.title" 
          placeholder="Voer een titel in..." 
          class="note-title"
          maxlength="100"
        />
        
        <textarea
          v-model="note.content"
          placeholder="Schrijf je notitie hier..."
          class="note-content"
          rows="4"
        ></textarea>

        <div class="note-metadata">
          <div class="dates-section">
            <div class="date-group">
              <label class="date-label">Aangemaakt</label>
              <input type="date" v-model="note.date" disabled class="date-input" />
            </div>
            <div class="date-group">
              <label class="date-label">Deadline</label>
              <input type="date" v-model="note.deadline" class="date-input" />
            </div>
          </div>

          <div class="tags-section">
            <input
              v-model="note.tagsInput"
              @blur="updateTags(note)"
              @keydown.enter.prevent="updateTags(note)"
              placeholder="Tags (gescheiden door komma's)"
              class="tags-input"
            />
            <div class="tags-display" v-if="note.tags && note.tags.length > 0">
              <span 
                v-for="tag in note.tags" 
                :key="tag" 
                class="tag-chip"
              >
                <span class="tag-icon">{{ iconForTag(tag) }}</span>
                {{ tag }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="filteredNotes.length === 0" class="empty-state">
      <div class="empty-icon">📝</div>
      <h3>Geen notities gevonden</h3>
      <p>Begin met het maken van je eerste notitie!</p>
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
  important: '⭐',
  urgent: '🚨',
  meeting: '👥',
  idea: '💡',
  todo: '✅',
  project: '🎯'
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
      note.content.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
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
  if (confirm('Weet je zeker dat je deze notitie wilt verwijderen?')) {
    notes.value = notes.value.filter((note) => note.id !== id);
    axios.delete(`http://localhost:8080/api/notes/${id}`).catch((err) => console.error(err));
  }
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
:root {
  --color-primary: #000000;
  --color-primary-hover: #333333;
  --color-secondary: #666666;
  --color-success: #333333;
  --color-warning: #555555;
  --color-danger: #000000;
  --color-info: #444444;
  --color-bg: #ffffff;
  --color-card: #ffffff;
  --color-text: #000000;
  --color-text-light: #555555;
  --color-text-muted: #888888;
  --shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.1);
  --shadow-md: 0 4px 6px -1px rgb(0 0 0 / 0.15), 0 2px 4px -2px rgb(0 0 0 / 0.1);
  --shadow-lg: 0 10px 15px -3px rgb(0 0 0 / 0.2), 0 4px 6px -4px rgb(0 0 0 / 0.1);
  --radius: 16px;
  --radius-md: 12px;
  --radius-sm: 8px;
  --transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
}

.home-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
  background: linear-gradient(135deg, #f8f8f8 0%, #ffffff 100%);
  min-height: 100vh;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.page-header {
  text-align: center;
  margin-bottom: 3rem;
}

.page-title {
  font-size: clamp(2rem, 5vw, 3rem);
  font-weight: 800;
  color: #000000;
  margin: 0 0 0.5rem 0;
  text-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.page-subtitle {
  color: #555555;
  font-size: 1.125rem;
  font-weight: 400;
  margin: 0;
}

.controls {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 2rem;
  background: #ffffff;
  padding: 1.5rem;
  border-radius: var(--radius);
  box-shadow: var(--shadow-lg);
  border: 2px solid #e5e5e5;
}

.primary-btn, .secondary-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: var(--radius-sm);
  font-weight: 600;
  font-size: 0.9rem;
  cursor: pointer;
  transition: var(--transition);
  white-space: nowrap;
}

.primary-btn {
  background: #000000;
  color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.primary-btn:hover {
  transform: translateY(-2px);
  background: #333333;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
}

.secondary-btn {
  background: #f8f8f8;
  color: #333333;
  border: 2px solid #e5e5e5;
}

.secondary-btn:hover {
  background: #e5e5e5;
  transform: translateY(-1px);
}

.btn-icon {
  font-size: 1rem;
}

.search-container {
  flex: 1;
  min-width: 250px;
}

.search-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 2px solid #e5e5e5;
  border-radius: var(--radius-sm);
  font-size: 0.95rem;
  transition: var(--transition);
  background: white;
}

.search-input:focus {
  outline: none;
  border-color: #000000;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.tag-select {
  padding: 0.75rem 1rem;
  border: 2px solid #e5e5e5;
  border-radius: var(--radius-sm);
  font-size: 0.95rem;
  transition: var(--transition);
  background: white;
  cursor: pointer;
  min-width: 150px;
}

.tag-select:focus {
  outline: none;
  border-color: #000000;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.notes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.note-card {
  background: var(--color-card);
  border-radius: var(--radius);
  box-shadow: var(--shadow-md);
  transition: var(--transition);
  position: relative;
  overflow: hidden;
  border: 2px solid #f0f0f0;
}

.note-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: #000000;
  transform: scaleX(0);
  transition: transform 0.3s ease;
}

.note-card:hover::before {
  transform: scaleX(1);
}

.note-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
  border-color: #d0d0d0;
}

.note-card.note-done {
  background: #f8f8f8;
  border-left: 6px solid #666666;
}

.note-card.note-done::before {
  background: #666666;
}

.note-card.note-urgent {
  background: #f0f0f0;
  border-left: 6px solid #333333;
  animation: gentle-pulse 3s ease-in-out infinite;
}

.note-card.note-urgent::before {
  background: #333333;
}

.note-card.note-has-deadline {
  border-right: 3px solid #555555;
}

@keyframes gentle-pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.01); }
}

.note-header {
  padding: 1rem 1rem 0;
}

.note-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.action-btn {
  width: 36px;
  height: 36px;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  transition: var(--transition);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
  background: #f8f8f8;
  border: 2px solid #e5e5e5;
}

.complete-btn {
  color: #333333;
}

.complete-btn.active,
.complete-btn:hover {
  background: #333333;
  color: white;
  transform: scale(1.05);
}

.urgent-btn {
  color: #555555;
}

.urgent-btn.active,
.urgent-btn:hover {
  background: #555555;
  color: white;
  transform: scale(1.05);
}

.archive-btn {
  color: #666666;
}

.archive-btn:hover {
  background: #666666;
  color: white;
  transform: scale(1.05);
}

.delete-btn {
  color: #000000;
}

.delete-btn:hover {
  background: #000000;
  color: white;
  transform: scale(1.05);
}

.note-title {
  width: 100%;
  padding: 1rem 1.5rem 0.5rem;
  font-size: 1.25rem;
  font-weight: 700;
  border: none;
  background: transparent;
  color: var(--color-text);
  transition: var(--transition);
  text-align: left;
}

.note-title:focus {
  outline: none;
  background: #f8f8f8;
  border-radius: var(--radius-sm);
}

.note-title::placeholder {
  color: var(--color-text-muted);
  font-weight: 400;
}

.note-content {
  width: 100%;
  padding: 0.5rem 1.5rem;
  border: none;
  background: transparent;
  color: var(--color-text);
  font-size: 0.95rem;
  line-height: 1.5;
  resize: vertical;
  min-height: 80px;
  font-family: inherit;
  transition: var(--transition);
}

.note-content:focus {
  outline: none;
  background: #f8f8f8;
  border-radius: var(--radius-sm);
}

.note-content::placeholder {
  color: var(--color-text-muted);
}

.note-metadata {
  padding: 1rem 1.5rem 1.5rem;
  border-top: 1px solid #e5e5e5;
  background: #fafafa;
}

.dates-section {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
  flex-wrap: wrap;
}

.date-group {
  flex: 1;
  min-width: 140px;
}

.date-label {
  display: block;
  font-size: 0.75rem;
  font-weight: 600;
  color: var(--color-text-light);
  margin-bottom: 0.25rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.date-input {
  width: 100%;
  padding: 0.5rem;
  border: 2px solid #e5e5e5;
  border-radius: var(--radius-sm);
  font-size: 0.85rem;
  transition: var(--transition);
  background: white;
}

.date-input:focus {
  outline: none;
  border-color: #000000;
  box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.1);
}

.date-input:disabled {
  background: #f5f5f5;
  color: var(--color-text-muted);
  cursor: not-allowed;
}

.tags-section {
  margin-top: 1rem;
}

.tags-input {
  width: 100%;
  padding: 0.5rem;
  border: 2px solid #e5e5e5;
  border-radius: var(--radius-sm);
  font-size: 0.85rem;
  transition: var(--transition);
  background: white;
  margin-bottom: 0.75rem;
}

.tags-input:focus {
  outline: none;
  border-color: #000000;
  box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.1);
}

.tags-display {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.tag-chip {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  background: #f0f0f0;
  color: var(--color-text);
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
  border: 2px solid #e5e5e5;
}

.tag-icon {
  font-size: 0.9rem;
}

.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  background: #ffffff;
  border-radius: var(--radius);
  box-shadow: var(--shadow-lg);
  border: 2px solid #e5e5e5;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
  opacity: 0.5;
  color: #888888;
}

.empty-state h3 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--color-text);
  margin: 0 0 0.5rem 0;
}

.empty-state p {
  color: var(--color-text-light);
  font-size: 1rem;
  margin: 0;
}

/* Responsive styles remain the same... */
@media (max-width: 768px) {
  .home-page {
    padding: 1rem;
  }
  
  .page-title {
    font-size: 2rem;
  }
  
  .controls {
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .primary-btn, .secondary-btn {
    justify-content: center;
  }
  
  .notes-grid {
    grid-template-columns: 1fr;
  }
  
  .dates-section {
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .date-group {
    min-width: unset;
  }
}

/* Custom scrollbar styling */
::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-track {
  background: #f0f0f0;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  background: #888888;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: #555555;
}
</style>